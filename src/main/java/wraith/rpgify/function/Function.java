package wraith.rpgify.function;

import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class Function extends AbstractVariable {

    protected final HashMap<String, AbstractVariable> parameters;
    protected final String name;

    public Function(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(null, "function");
        this.value = this;
        this.name = functionName;
        this.parameters = parameters;
    }

    public static Function factory(String name, HashMap<String, AbstractVariable> parameters) {
        switch(name) {
            case "incremental_range":
                if (hasValidParameters(parameters, IncrementalRangeFunction.mandatoryParameters)) {
                    return new IncrementalRangeFunction(name, parameters);
                }
                break;
            case "range":
                if (hasValidParameters(parameters, RangeFunction.mandatoryParameters)) {
                    return new RangeFunction(name, parameters);
                }
                break;
            case "random_chance":
                if (hasValidParameters(parameters, RandomChanceFunction.mandatoryParameters)) {
                    return new RandomChanceFunction(name, parameters);
                }
                break;
            case "get_item":
                if (hasValidParameters(parameters, GetItemFunction.mandatoryParameters)) {
                    return new GetItemFunction(name, parameters);
                }
                break;
            case "get_enchantment":
                if (hasValidParameters(parameters, GetEnchantmentFunction.mandatoryParameters)) {
                    return new GetEnchantmentFunction(name, parameters);
                }
                break;
            case "has_enchantment":
                if (hasValidParameters(parameters, HasEnchantmentFunction.mandatoryParameters)) {
                    return new HasEnchantmentFunction(name, parameters);
                }
                break;
            case "create_region":
                if (hasValidParameters(parameters, CreateRegionFunction.mandatoryParameters)) {
                    return new CreateRegionFunction(name, parameters);
                }
                break;
            case "is_in_region":
                if (hasValidParameters(parameters, IsInRegionFunction.mandatoryParameters)) {
                    return new IsInRegionFunction(name, parameters);
                }
                break;
            case "pick_random":
                if (hasValidParameters(parameters, PickRandomFunction.mandatoryParameters)) {
                    return new PickRandomFunction(name, parameters);
                }
                break;
        }
        return null;
    }

    protected static boolean hasValidParameters(HashMap<String, AbstractVariable> parameters, HashMap<String, HashSet<String>> mandatoryParameters) {
        for (Map.Entry<String, HashSet<String>> param : mandatoryParameters.entrySet()) {
            if(!parameters.containsKey(param.getKey()) || !param.getValue().contains(parameters.get(param.getKey()).getType())) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isValidParameter(String key, HashMap<String, HashSet<String>> mandatoryParameters, HashMap<String, HashSet<String>> optionalParameters) {
        return mandatoryParameters.containsKey(key) || optionalParameters.containsKey(key);
    }

}
