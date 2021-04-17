package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.MapVariable;

import java.util.HashMap;
import java.util.HashSet;

public class CreateMap extends Function {

    private static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<>();
    private static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>(){{
        put("values", new HashSet<String>(){{
            add("hashmap");
        }});
    }};

    public CreateMap(String functionName, HashMap<String, AbstractVariable> parameters){
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        if (parameters.containsKey("values")) {
            return new MapVariable(parameters.get("values"));
        }
        return new MapVariable();
    }

}
