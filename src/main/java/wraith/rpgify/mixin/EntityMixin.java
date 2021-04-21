package wraith.rpgify.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.rpgify.entity.CustomEntities;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract UUID getUuid();

    @Inject(method = "remove", at = @At("TAIL"))
    public void remove(CallbackInfo ci) {
        CustomEntities.INGAME_ENTITIES.remove(getUuid());
    }

}
