package wraith.rpgify.event;

import net.minecraft.entity.player.PlayerEntity;
import wraith.rpgify.event.listener.PlayerEnterRegionListener;
import wraith.rpgify.event.listener.RPGListener;
import wraith.rpgify.variable.Region;

public class PlayerEnterRegionEvent extends RPGEvent {

    private static PlayerEnterRegionEvent INSTANCE = null;

    public static PlayerEnterRegionEvent getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerEnterRegionEvent();
        }
        return INSTANCE;
    }

    public void onEnter(PlayerEntity player, Region region) {
        for (RPGListener listener : listeners) {
            ((PlayerEnterRegionListener)listener).onEnter(player, region);
        }
    }

    public void onLeave(PlayerEntity player, Region region) {
        for (RPGListener listener : listeners) {
            ((PlayerEnterRegionListener)listener).onLeave(player, region);
        }
    }

}
