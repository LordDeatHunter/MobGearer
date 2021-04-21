package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PickRandomFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("from", new HashSet<String>(){{
            add("hashset");
            add("arraylist");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public PickRandomFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    public static Object pickRandom(Object collection) {
        if (collection == null) {
            return null;
        }
        if (collection instanceof ArrayList) {
            ArrayList list = ((ArrayList)collection);
            if (list.size() == 0) {
                return null;
            }
            return list.get(RangeFunction.getIntegerRange(0, list.size() - 1));
        }
        if (collection instanceof HashSet) {
            ArrayList list = new ArrayList((HashSet)collection);
            if (list.size() == 0) {
                return null;
            }
            return list.get(RangeFunction.getIntegerRange(0, list.size() - 1));
        }
        return null;
    }

    @Override
    public Object getValue() {
        return pickRandom(this.parameters.get("from").getValue());
    }

}