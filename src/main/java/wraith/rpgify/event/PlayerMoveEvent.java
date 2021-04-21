package wraith.rpgify.event;

import net.minecraft.entity.player.PlayerEntity;
import wraith.rpgify.event.listener.PlayerMoveListener;
import wraith.rpgify.event.listener.RPGListener;

public class PlayerMoveEvent extends RPGEvent {

    private static PlayerMoveEvent INSTANCE = null;

    public static PlayerMoveEvent getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerMoveEvent();
        }
        return INSTANCE;
    }

    public void beforeMove(PlayerEntity player) {
        for (RPGListener listener : listeners) {
            ((PlayerMoveListener)listener).beforeMove(player);
        }
    }

    public void afterMove(PlayerEntity player) {
        for (RPGListener listener : listeners) {
            ((PlayerMoveListener)listener).afterMove(player);
        }
    }


}
