package wraith.rpgify.function;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wraith.rpgify.item.CustomItems;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class GetItemFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("item", new HashSet<String>(){{
            add("string");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>(){{
        put("amount", new HashSet<String>(){{
            add("integer");
        }});
    }};

    public GetItemFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    public ItemStack getItem() {
        ItemStack stack;
        int amount = 1;
        if (this.parameters.containsKey("amount")) {
            amount = (int) this.parameters.get("amount").getValue();
        }
        String itemId = String.valueOf(this.parameters.get("item").getValue());
        if (!itemId.contains(":")) {
            stack = CustomItems.getItem(itemId).getStack();
            stack.setCount(amount);
        } else {
            stack = new ItemStack(Registry.ITEM.get(new Identifier(itemId)), amount);
        }
        return stack;
    }

    @Override
    public Object getValue() {
        return getItem();
    }

    @Override
    public String getType() {
        return "itemstack";
    }
}