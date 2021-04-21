package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.Region;

import java.util.HashMap;
import java.util.HashSet;

public class GetRandomPositionInRegionFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("region", new HashSet<String>(){{
            add("region");
        }});
    }};
    private static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>() {{
        put("safe", new HashSet<String>() {{
            add("boolean");
        }});
    }};

    public GetRandomPositionInRegionFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        if (this.parameters.containsKey("safe") && Boolean.parseBoolean(String.valueOf(this.parameters.get("safe").getValue()))) {
            return ((Region)this.parameters.get("region").getValue()).getRandomSafePositionInside();
        }
        return ((Region)this.parameters.get("region").getValue()).getRandomPositionInside();
    }

    @Override
    public String getType() {
        return "blockpos";
    }

}
