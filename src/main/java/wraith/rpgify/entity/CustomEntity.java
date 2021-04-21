package wraith.rpgify.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import wraith.rpgify.MobGroups;
import wraith.rpgify.MobUtils;
import wraith.rpgify.function.PickRandomFunction;
import wraith.rpgify.roll.RollPicker;
import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.ConditionVariable;

import java.util.UUID;

public class CustomEntity extends AbstractVariable {

    private final String ID;
    private AbstractVariable mobType;
    private String name;
    private AbstractVariable displayName;
    private AbstractVariable maxHealth;
    private AbstractVariable health;
    private RollPicker equipment;
    private RollPicker drops;
    private AbstractVariable spawnPos;
    private AbstractVariable vanillaDrops;
    private AbstractVariable spawnEvent;
    private ConditionVariable spawnCondition;

    private UUID uuid = null;

    public CustomEntity(String ID,
                        AbstractVariable mobType,
                        String name,
                        AbstractVariable displayName,
                        AbstractVariable maxHealth,
                        AbstractVariable health,
                        RollPicker equipment,
                        RollPicker drops,
                        AbstractVariable spawnPos,
                        AbstractVariable spawnEvent,
                        AbstractVariable vanillaDrops,
                        ConditionVariable spawnCondition) {
        super(null, "entity");
        this.ID = ID;
        this.mobType = mobType;
        this.name = name;
        this.displayName = displayName;
        this.maxHealth = maxHealth;
        this.health = health;
        this.equipment = equipment;
        this.drops = drops;
        this.spawnEvent = spawnEvent;
        this.spawnPos = spawnPos;
        this.vanillaDrops = vanillaDrops;
        this.spawnCondition = spawnCondition;
    }

    public EntityType<?> getEntityType() {
        return getEntityType(String.valueOf(mobType.getValue()));
    }

    public static EntityType<?> getEntityType(String mobType) {
        if (mobType.contains(":")) {
            return Registry.ENTITY_TYPE.get(new Identifier(mobType));
        } else {
            return Registry.ENTITY_TYPE.get(new Identifier((String) PickRandomFunction.pickRandom(MobGroups.get(mobType))));
        }
    }

    public void spawnEntity(ServerPlayerEntity player) {
        EntityType<?> entityType = getEntityType();
        Entity entity = entityType.create(player.world);
        if (entity == null) {
            return;
        }
        entity.setWorld(player.world);
        entity = transformEntity(entity);
        this.spawnCondition.setVariable("player", player);
        this.spawnCondition.setVariable("entity", entity);
        this.spawnCondition.setVariable("customentity", this);
        if (!(boolean)this.spawnCondition.getValue()) {
            entity.remove();
            return;
        }
        player.world.spawnEntity(entity);
        CustomEntities.INGAME_ENTITIES.put(entity.getUuid(), this);
    }

    public Entity transformEntity(Entity entity) {
        entity.setCustomName(Text.of(this.name));
        entity.setCustomNameVisible(Boolean.parseBoolean(String.valueOf(this.displayName.getValue())));

        if (entity instanceof LivingEntity) {
            MobUtils.setMaxHealth((LivingEntity) entity, Float.parseFloat(String.valueOf(this.maxHealth.getValue())));
            ((LivingEntity) entity).setHealth(Float.parseFloat(String.valueOf(this.health.getValue())));
        }

        BlockPos pos = (BlockPos) spawnPos.getValue();
        entity.updatePosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);

        return entity;
    }

    public String getID() {
        return this.ID;
    }

}
