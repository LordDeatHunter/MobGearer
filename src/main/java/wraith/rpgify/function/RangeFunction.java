package wraith.rpgify.function;

import wraith.rpgify.Utils;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class RangeFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("min", new HashSet<String>(){{
            add("integer");
            add("float");
        }});
        put("max", new HashSet<String>(){{
            add("integer");
            add("float");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>(){{
        put("return_type", new HashSet<String>(){{
            add("string");
        }});
    }};

    public RangeFunction(String name, HashMap<String, AbstractVariable> parameters) {
        super(name, parameters);
    }

    public float getRange() {
        String returnType = "";
        if (this.parameters.containsKey("return_type")){
            returnType = (String)this.parameters.get("return_type").getValue();
        }
        Object minO = this.parameters.get("min").getValue();
        Object maxO = this.parameters.get("max").getValue();
        float min = Float.parseFloat(String.valueOf(minO));
        float max = Float.parseFloat(String.valueOf(maxO));

        if ("whole_number".equalsIgnoreCase(returnType) || "integer".equalsIgnoreCase(returnType)) {
            return (float)getIntegerRange((int)min, (int)max);
        } else {
            return getDecimalRange(min, max);
        }
    }

    public static float getDecimalRange(float min, float max) {
        return Utils.getRandomFloatInRange(min, max);
    }

    public static int getIntegerRange(int min, int max) {
        return Utils.getRandomIntInRange(min, max);
    }

    public static int getIncrementalRange(int min, int max, int increment) {
        int difference = (int) Math.ceil((max - min) / (float)increment);
        return min + Utils.getRandomIntInRange(0, difference) * increment;
    }

    public static float getIncrementalRange(float min, float max, float increment) {
        int difference = (int) Math.ceil((max - min) / increment);
        return min + Utils.getRandomIntInRange(0, difference) * increment;
    }

    @Override
    public Object getValue() {
        return getRange();
    }

    @Override
    public String getType() {
        if (this.parameters.containsKey("return_type")){
            String rt = (String) this.parameters.get("return_type").getValue();
            if ("integer".equals(rt) || "whole_number".equals(rt)) {
                return "integer";
            }
        }
        return "float";
    }

}