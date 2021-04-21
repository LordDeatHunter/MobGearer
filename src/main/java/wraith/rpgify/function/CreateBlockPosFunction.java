package wraith.rpgify.function;

import net.minecraft.util.math.BlockPos;
import wraith.rpgify.variable.AbstractVariable;

import java.util.HashMap;
import java.util.HashSet;

public class CreateBlockPosFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("x", new HashSet<String>(){{
            add("integer");
        }});
        put("y", new HashSet<String>(){{
            add("integer");
        }});
        put("z", new HashSet<String>(){{
            add("integer");
        }});
    }};
    public static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public CreateBlockPosFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        int x = Integer.parseInt(String.valueOf(parameters.get("x")));
        int y = Integer.parseInt(String.valueOf(parameters.get("y")));
        int z = Integer.parseInt(String.valueOf(parameters.get("z")));
        return new BlockPos(x, y, z);
    }

    @Override
    public String getType() {
        return "blockpos";
    }

}