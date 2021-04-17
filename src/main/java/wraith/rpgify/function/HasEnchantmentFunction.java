package wraith.rpgify.function;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wraith.rpgify.RPGify;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class HasEnchantmentFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("enchantment", new HashSet<String>(){{
            add("string");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>(){{
        put("level", new HashSet<String>(){{
            add("integer");
        }});
    }};

    private ItemStack item = null;

    public HasEnchantmentFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public Object getValue() {
        if (item == null) {
            RPGify.LOGGER.error("has_enchantment() function ran without invalid item.");
            return false;
        }
        if (item == ItemStack.EMPTY) {
            return false;
        }
        Enchantment enchantment = Registry.ENCHANTMENT.get(new Identifier(String.valueOf(this.parameters.get("enchantment"))));
        return EnchantmentHelper.getLevel(enchantment, this.item) > 0;
    }

    @Override
    public String getType() {
        return "boolean";
    }
}
