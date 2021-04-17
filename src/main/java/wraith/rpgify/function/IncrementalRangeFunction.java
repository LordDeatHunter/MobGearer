package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class IncrementalRangeFunction extends RangeFunction {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("min", new HashSet<String>(){{
            add("integer");
            add("float");
        }});
        put("max", new HashSet<String>(){{
            add("integer");
            add("float");
        }});
        put("increment_chance", new HashSet<String>(){{
            add("integer");
            add("float");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>(){{
        put("return_type", new HashSet<String>(){{
            add("string");
        }});
    }};

    public IncrementalRangeFunction(String name, HashMap<String, AbstractVariable> parameters) {
        super(name, parameters);
    }

    @Override
    public float getRange() {
        String returnType = "";

        if (this.parameters.containsKey("return_type")){
            returnType = (String)this.parameters.get("return_type").getValue();
        }

        Object minO = this.parameters.get("min").getValue();
        Object maxO = this.parameters.get("max").getValue();
        Object incO = this.parameters.get("increment_amount").getValue();
        float min = Float.parseFloat(String.valueOf(minO));
        float max = Float.parseFloat(String.valueOf(maxO));
        float increment = Float.parseFloat(String.valueOf(incO));

        if ("whole_number".equalsIgnoreCase(returnType) || "integer".equalsIgnoreCase(returnType)) {
            return getIncrementalRange((int) min, (int) max, (int) increment);
        } else {
            return getIncrementalRange(min, max, increment);
        }
    }

    @Override
    public Object getValue() {
        return getRange();
    }


}
