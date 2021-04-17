package wraith.rpgify.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;
import wraith.rpgify.CustomEnchantment;
import wraith.rpgify.Utils;
import wraith.rpgify.roll.RollPicker;
import wraith.rpgify.variable.AbstractVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomItem {

    private AbstractVariable stack;
    private String name;
    private List<String> lore;
    private RollPicker enchantments;

    public CustomItem(AbstractVariable stack, String name, List<String> lore, RollPicker enchants) {
        this.stack = stack;
        this.name = name;
        this.lore = lore;
        this.enchantments = enchants;
    }

    private ItemStack getStackValue() {
        Object stack = this.stack.getValue();
        if (stack instanceof ItemStack) {
            return (ItemStack) stack;
        } else {
            ArrayList<ItemStack> stacks = (ArrayList<ItemStack>) stack;
            return stacks.get(Utils.getRandomIntInRange(0, stacks.size() - 1));
        }
    }

    public ItemStack getStack() {
        ItemStack stack = getStackValue();
        if (this.name != null) {
            stack.setCustomName(Text.of(name));
        }
        if (!lore.isEmpty()) {
            CompoundTag tag = stack.getOrCreateSubTag("display");
            ListTag list = new ListTag();
            if (tag.contains("Lore")) {
                list = tag.getList("Lore", 8);
            }
            for (String line : lore) {
                list.add(StringTag.of("\""+line+"\""));
            }
            tag.put("Lore", list);
        }

        HashMap<Enchantment, Integer> enchantMap = new HashMap<>();
        if (enchantments != null) {
            ArrayList<Object> variables = (ArrayList<Object>) this.enchantments.getValue();
            for (Object variable : variables) {
                CustomEnchantment enchantment = (CustomEnchantment) variable;
                if (enchantMap.containsKey(enchantment.getEnchantment())) {
                    enchantMap.put(enchantment.getEnchantment(), Math.max(enchantMap.get(enchantment.getEnchantment()), enchantment.getLevel()));
                } else {
                    enchantMap.put(enchantment.getEnchantment(), enchantment.getLevel());
                }
            }
        }
        EnchantmentHelper.set(enchantMap, stack);

        return stack;
    }

}
