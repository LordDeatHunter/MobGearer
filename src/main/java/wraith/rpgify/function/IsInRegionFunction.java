package wraith.rpgify.function;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import wraith.rpgify.entity.CustomEntity;
import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.Region;

import java.util.HashMap;
import java.util.HashSet;

public class IsInRegionFunction extends Function {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("region", new HashSet<String>(){{
            add("region");
        }});
    }};
    private static HashMap<String, HashSet<String>> optionalParameters = new HashMap<>();

    public IsInRegionFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    @Override
    public Object getValue() {
        if (!this.variables.containsKey("entity")) {
            return false;
        }
        Entity entity = (Entity) this.variables.get("entity");
        BlockPos pos = entity.getBlockPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        Region region = (Region) this.parameters.get("region");
        return region.isInside(x, y, z);
    }

    @Override
    public String getType() {
        return "boolean";
    }

}
