package wraith.rpgify.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.rpgify.event.PlayerEnterRegionEvent;
import wraith.rpgify.event.PlayerMoveEvent;
import wraith.rpgify.variable.Region;

import java.util.HashSet;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    private HashSet<Region> regions = new HashSet<>();
    private int counter = 0;
    private final int cooldown = 1;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void beforeMove(CallbackInfo ci) {
        PlayerMoveEvent.getInstance().beforeMove((PlayerEntity)(Object)this);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void afterMove(CallbackInfo ci) {
        PlayerMoveEvent.getInstance().afterMove((PlayerEntity)(Object)this);
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.world.isClient) {
            return;
        }

        if (this.counter == 0) {
            HashSet<Region> newRegions = Region.findRegions(((PlayerEntity) (Object) this).getBlockPos());
            if (!newRegions.equals(regions)) {
                for (Region region : newRegions) {
                    if (!this.regions.contains(region)) {
                        PlayerEnterRegionEvent.getInstance().onEnter((PlayerEntity) (Object) this, region);
                    } else {
                        this.regions.remove(region);
                    }
                }
                for (Region region : this.regions) {
                    PlayerEnterRegionEvent.getInstance().onLeave((PlayerEntity) (Object) this, region);
                }
                this.regions = newRegions;
            }
        }
        this.counter = (this.counter + 1) % cooldown;
    }

}
