package wraith.rpgify.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wraith.rpgify.MobGroups;
import wraith.rpgify.MobUtils;
import wraith.rpgify.function.PickRandomFunction;
import wraith.rpgify.roll.RollPicker;
import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.ConditionVariable;

import java.util.UUID;

public class CustomEntity {

    private String mobGroup;
    private AbstractVariable name;
    private AbstractVariable displayName;
    private AbstractVariable maxHealth;
    private AbstractVariable health;
    private RollPicker equipment;
    private RollPicker drops;
    private AbstractVariable vanillaDrops;
    private ConditionVariable spawnCondition;
    private String uuid = null;

    public CustomEntity(String mobGroup,
                        AbstractVariable name,
                        AbstractVariable displayName,
                        AbstractVariable maxHealth,
                        AbstractVariable health,
                        RollPicker equipment,
                        RollPicker drops,
                        AbstractVariable vanillaDrops,
                        ConditionVariable spawnCondition) {
        this.mobGroup = mobGroup;
        this.name = name;
        this.displayName = displayName;
        this.maxHealth = maxHealth;
        this.health = health;
        this.equipment = equipment;
        this.drops = drops;
        this.vanillaDrops = vanillaDrops;
        this.spawnCondition = spawnCondition;
    }

    public EntityType<?> getEntity() {
        if (mobGroup.contains(":")) {
            return Registry.ENTITY_TYPE.get(new Identifier(mobGroup));
        } else {
            return Registry.ENTITY_TYPE.get(new Identifier((String) PickRandomFunction.pickRandom(MobGroups.get(mobGroup))));
        }
    }

    public Entity transformEntity(Entity entity) {
        entity.setCustomName(Text.of((String) this.name.getValue()));
        entity.setCustomNameVisible(Boolean.parseBoolean(String.valueOf(this.displayName.getValue())));
        if (entity instanceof LivingEntity) {
            MobUtils.setMaxHealth((LivingEntity) entity, Float.parseFloat(String.valueOf(this.maxHealth.getValue())));
            ((LivingEntity) entity).setHealth(Float.parseFloat(String.valueOf(this.health.getValue())));
        }
        CompoundTag tag = new CompoundTag();
        entity.toTag(tag);
        this.uuid = UUID.randomUUID().toString();
        tag.putString("rpgify", uuid);
        entity.fromTag(tag);

        return entity;
    }

}
