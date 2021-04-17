package wraith.rpgify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;

public class MobUtils {

    private MobUtils() {}

    public static void setMaxHealth(LivingEntity entity, float maxHealth) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
    }

}
