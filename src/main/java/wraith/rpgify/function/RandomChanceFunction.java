package wraith.rpgify.function;

import wraith.rpgify.Utils;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class RandomChanceFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("chance", new HashSet<String>(){{
            add("float");
            add("integer");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public RandomChanceFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    public static boolean getRandomChance(float chance) {
        if (chance == 0) {
            return false;
        }
        return chance >= Utils.RANDOM.nextFloat();
    }

    @Override
    public Object getValue() {
        return getRandomChance((float) this.parameters.get("chance").getValue());
    }

    @Override
    public String getType() {
        return "boolean";
    }
}
