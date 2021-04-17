package wraith.rpgify.event;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

public interface MobSpawnEventListener {

    void beforeSpawn(ServerWorld world, Entity entity);
    void afterSpawn(ServerWorld world, Entity entity);

}
