package wraith.rpgify.event.listener;

import net.minecraft.entity.player.PlayerEntity;
import wraith.rpgify.variable.Region;

public interface PlayerEnterRegionListener extends RPGListener {

    void onEnter(PlayerEntity player, Region region);
    void onLeave(PlayerEntity player, Region region);

}
