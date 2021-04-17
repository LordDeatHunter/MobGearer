package wraith.rpgify;

import net.minecraft.enchantment.Enchantment;

public class CustomEnchantment {

    private final Enchantment enchantment;
    private final int level;

    public CustomEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public Enchantment getEnchantment() {
        return this.enchantment;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != CustomEnchantment.class) {
            return false;
        }
        CustomEnchantment enchant = (CustomEnchantment) obj;
        return enchant.getEnchantment().equals(this.enchantment) && enchant.getLevel() == this.level;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + enchantment.hashCode();
        hash = 31 * hash + Integer.hashCode(level);
        return hash;
    }

}
