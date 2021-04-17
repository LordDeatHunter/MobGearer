package wraith.rpgify.event;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

import java.util.HashSet;

public class MobSpawnEvent {

    private MobSpawnEvent() {}

    private static MobSpawnEvent INSTANCE = null;
    private final HashSet<MobSpawnEventListener> listeners = new HashSet<>();

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
        for (MobSpawnEventListener listener : listeners) {
           listener.beforeSpawn(world, entity);
        }

        for (MobSpawnEventListener listener : listeners) {
           listener.afterSpawn(world, entity);
        }
    }

}
