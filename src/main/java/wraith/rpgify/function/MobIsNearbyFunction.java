package wraith.rpgify.function;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import wraith.rpgify.entity.CustomEntities;
import wraith.rpgify.entity.CustomEntity;
import wraith.rpgify.variable.AbstractVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MobIsNearbyFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("mob_type", new HashSet<String>(){{
            add("string");
            add("this");
        }});
        put("from_position", new HashSet<String>(){{
            add("blockpos");
        }});
        put("radius", new HashSet<String>(){{
            add("integer");
        }});
        put("in_world", new HashSet<String>(){{
            add("string");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public MobIsNearbyFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        if (!this.parameters.containsKey("radius")) {
            return null;
        }
        int radius = Integer.parseInt(String.valueOf(this.parameters.get("radius").getValue()));

        if (this.variables.containsKey("entity") && this.variables.containsKey("player")) {
            PlayerEntity player = (PlayerEntity) this.variables.get("player");
            CustomEntity customEntity = (CustomEntity) this.variables.get("customentity");
            Entity entity = (Entity) this.variables.get("entity");
            BlockPos pos = entity.getBlockPos();
            World world = player.world;
            Box box = new Box(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius);
            List<? extends Entity> entities = world.getOtherEntities(null, box);
            for (Entity targetEntity : entities) {
                if (CustomEntities.INGAME_ENTITIES.containsKey(targetEntity.getUuid()) && CustomEntities.INGAME_ENTITIES.get(targetEntity.getUuid()).getID().equals(customEntity.getID())) {
                    return true;
                }
            }
        } else {
            if (!this.parameters.containsKey("mob_type")) {
                return null;
            }
            EntityType<?> entityType = CustomEntity.getEntityType(String.valueOf(this.parameters.get("mob_type").getValue()));

            if (!this.parameters.containsKey("from_position")) {
                return null;
            }
            BlockPos pos = (BlockPos) this.parameters.get("from_position").getValue();
            Box box = new Box(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius);
            World world = null;
            if (!this.parameters.containsKey("in_world")) {
                return null;
            }
            String worldName = String.valueOf(this.parameters.get("in_world"));
            for (World worlds : MinecraftClient.getInstance().getServer().getWorlds()) {
                if (worlds.getRegistryKey().getValue().toString().equals(worldName)) {
                    world = worlds;
                }
            }
            if (world == null) {
                return null;
            }

            List<? extends Entity> entities = world.getEntitiesByType(entityType, box, EntityPredicates.maxDistance(pos.getX(), pos.getY(), pos.getZ(), radius));
            for (Entity entity : entities) {
                if (entity.getType().equals(entityType)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getType() {
        return "boolean";
    }

}
