package wraith.rpgify.function;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import wraith.rpgify.CustomEnchantment;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class GetEnchantmentFunction extends Function {

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

    public GetEnchantmentFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        String enchantment = (String) this.parameters.get("enchantment").getValue();
        int level = 1;
        if (this.parameters.containsKey("level")) {
            level = Integer.parseInt(String.valueOf(this.parameters.get("level").getValue()));
        }
        return new CustomEnchantment(Registry.ENCHANTMENT.get(new Identifier(enchantment)), level);
    }

    @Override
    public String getType() {
        return "customenchantment";
    }

}
