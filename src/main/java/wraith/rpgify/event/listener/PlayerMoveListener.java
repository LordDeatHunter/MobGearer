package wraith.rpgify.event.listener;

import net.minecraft.entity.player.PlayerEntity;

public interface PlayerMoveListener extends RPGListener {

    void beforeMove(PlayerEntity player);
    void afterMove(PlayerEntity player);

}