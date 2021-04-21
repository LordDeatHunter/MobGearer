package wraith.rpgify.event.listener;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

public interface MobSpawnEventListener extends RPGListener {

    void beforeSpawn(ServerWorld world, Entity entity);
    void afterSpawn(ServerWorld world, Entity entity);

}
