package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.Region;

import java.util.HashMap;
import java.util.HashSet;

public class CreateRegionFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("name", new HashSet<String>(){{
            add("string");
        }});
        put("min_x", new HashSet<String>(){{
            add("integer");
        }});
        put("min_y", new HashSet<String>(){{
            add("integer");
        }});
        put("min_z", new HashSet<String>(){{
            add("integer");
        }});
        put("max_x", new HashSet<String>(){{
            add("integer");
        }});
        put("max_y", new HashSet<String>(){{
            add("integer");
        }});
        put("max_z", new HashSet<String>(){{
            add("integer");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public CreateRegionFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        String name = String.valueOf(parameters.get("name").getValue());
        String dimension = String.valueOf(parameters.get("dimension").getValue());
        int minX = Integer.parseInt(String.valueOf(parameters.get("min_x").getValue()));
        int minY = Integer.parseInt(String.valueOf(parameters.get("min_y").getValue()));
        int minZ = Integer.parseInt(String.valueOf(parameters.get("min_z").getValue()));
        int maxX = Integer.parseInt(String.valueOf(parameters.get("max_x").getValue()));
        int maxY = Integer.parseInt(String.valueOf(parameters.get("max_y").getValue()));
        int maxZ = Integer.parseInt(String.valueOf(parameters.get("max_z").getValue()));
        return new Region(name, dimension, minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public String getType() {
        return "region";
    }

}
