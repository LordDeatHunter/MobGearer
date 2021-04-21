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

    public static Function factory(String name, HashMap<String, AbstractVariable> parameters, boolean override) {
        switch(name) {
            case "incremental_range":
                if (override || hasValidParameters(parameters, IncrementalRangeFunction.mandatoryParameters)) {
                    return new IncrementalRangeFunction(name, parameters);
                }
                break;
            case "range":
                if (override || hasValidParameters(parameters, RangeFunction.mandatoryParameters)) {
                    return new RangeFunction(name, parameters);
                }
                break;
            case "random_chance":
                if (override || hasValidParameters(parameters, RandomChanceFunction.mandatoryParameters)) {
                    return new RandomChanceFunction(name, parameters);
                }
                break;
            case "get_item":
                if (override || hasValidParameters(parameters, GetItemFunction.mandatoryParameters)) {
                    return new GetItemFunction(name, parameters);
                }
                break;
            case "get_enchantment":
                if (override || hasValidParameters(parameters, GetEnchantmentFunction.mandatoryParameters)) {
                    return new GetEnchantmentFunction(name, parameters);
                }
                break;
            case "has_enchantment":
                if (override || hasValidParameters(parameters, HasEnchantmentFunction.mandatoryParameters)) {
                    return new HasEnchantmentFunction(name, parameters);
                }
                break;
            case "create_region":
                if (override || hasValidParameters(parameters, CreateRegionFunction.mandatoryParameters)) {
                    return new CreateRegionFunction(name, parameters);
                }
                break;
            case "is_in_region":
                if (override || hasValidParameters(parameters, IsInRegionFunction.mandatoryParameters)) {
                    return new IsInRegionFunction(name, parameters);
                }
                break;
            case "pick_random":
                if (override || hasValidParameters(parameters, PickRandomFunction.mandatoryParameters)) {
                    return new PickRandomFunction(name, parameters);
                }
                break;
            case "get_random_position_in_region":
                if (override || hasValidParameters(parameters, GetRandomPositionInRegionFunction.mandatoryParameters)) {
                    return new GetRandomPositionInRegionFunction(name, parameters);
                }
                break;
            case "get_random_safe_position_in_region":
                parameters.put("safe", AbstractVariable.of("true"));
                if (override || hasValidParameters(parameters, GetRandomPositionInRegionFunction.mandatoryParameters)) {
                    return new GetRandomPositionInRegionFunction(name, parameters);
                }
                break;
            case "create_block_pos":
                if (override || hasValidParameters(parameters, CreateBlockPosFunction.mandatoryParameters)) {
                    return new CreateBlockPosFunction(name, parameters);
                }
                break;
            case "mob_is_nearby":
                if (override || hasValidParameters(parameters, MobIsNearbyFunction.mandatoryParameters)) {
                    return new MobIsNearbyFunction(name, parameters);
                }
                break;
            case "on_player_enter_region":
                parameters.put("enter_or_leave", AbstractVariable.of("\"enter\""));
                if (override || hasValidParameters(parameters, OnPlayerEnterRegionFunction.mandatoryParameters)) {
                    return new OnPlayerEnterRegionFunction(name, parameters);
                }
                break;
            case "on_player_leave_region":
                parameters.put("enter_or_leave", AbstractVariable.of("\"leave\""));
                if (override || hasValidParameters(parameters, OnPlayerEnterRegionFunction.mandatoryParameters)) {
                    return new OnPlayerEnterRegionFunction(name, parameters);
                }
                break;
            case "spawn_entity":
                if (override || hasValidParameters(parameters, SpawnEntityFunction.mandatoryParameters)) {
                    return new SpawnEntityFunction(name, parameters);
                }
                break;
        }
        return null;
    }

    protected static boolean hasValidParameters(HashMap<String, AbstractVariable> parameters, HashMap<String, HashSet<String>> mandatoryParameters) {
        for (Map.Entry<String, HashSet<String>> param : mandatoryParameters.entrySet()) {
            if (parameters.get(param.getKey()) == null) {
                System.out.println("pisspack");
            }
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
