package wraith.rpgify.function;

import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import wraith.rpgify.entity.CustomEntities;
import wraith.rpgify.entity.CustomEntity;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class SpawnEntityFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("entity", new HashSet<String>(){{
            add("string");
        }});
        put("position", new HashSet<String>(){{
            add("blockpos");
        }});
    }};
    private static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public SpawnEntityFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        if (!this.variables.containsKey("player") || !(this.variables.get("player") instanceof ServerPlayerEntity)) {
            return null;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) this.variables.get("player");
        if (player.world.isClient) {
            return null;
        }
        if (!this.parameters.containsKey("entity")) {
            return null;
        }
        AbstractVariable entity = this.parameters.get("entity");
        if ("string".equals(entity.getType())) {
            String entityID = String.valueOf(entity.getValue());
            if (entityID.contains(":") && this.parameters.containsKey("position")) {
                BlockPos blockPos = (BlockPos) this.parameters.get("position").getValue();
                Registry.ENTITY_TYPE.get(new Identifier(entityID)).spawn((ServerWorld) player.world, null, null, player, blockPos, SpawnReason.NATURAL, false, false);
            } else if (CustomEntities.entityExists(entityID)) {
                CustomEntities.getEntity(entityID).spawnEntity(player);
            }
        } else if ("entity".equals(entity.getType())) {
            CustomEntity customEntity = (CustomEntity) entity.getValue();
            customEntity.spawnEntity(player);
        }
        return null;
    }

}
