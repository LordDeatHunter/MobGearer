package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.SetVariable;

import java.util.HashMap;
import java.util.HashSet;

public class CreateSet extends Function {

    private static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<>();
    private static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>(){{
        put("values", new HashSet<String>(){{
            add("arraylist");
        }});
    }};

    public CreateSet(String functionName, HashMap<String, AbstractVariable> parameters){
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        return new SetVariable();
    }

}
