package wraith.rpgify.event;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import wraith.rpgify.event.listener.MobSpawnEventListener;
import wraith.rpgify.event.listener.RPGListener;

public class MobSpawnEvent extends RPGEvent {

    private MobSpawnEvent() {}

    private static MobSpawnEvent INSTANCE = null;

    public static MobSpawnEvent getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MobSpawnEvent();
        }
        return INSTANCE;
    }

    public void registerListener(MobSpawnEventListener listener) {
        listeners.add(listener);
    }

    public void onSpawn(ServerWorld world, Entity entity) {
        for (RPGListener listener : listeners) {
            ((MobSpawnEventListener)listener).beforeSpawn(world, entity);
        }

        for (RPGListener listener : listeners) {
            ((MobSpawnEventListener)listener).afterSpawn(world, entity);
        }
    }

}
