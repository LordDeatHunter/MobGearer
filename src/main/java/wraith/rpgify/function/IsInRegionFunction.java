package wraith.rpgify.function;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
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
    private Entity entity = null;

    public IsInRegionFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Object getValue() {
        if (entity == null) {
            return false;
        }
        BlockPos pos = this.entity.getBlockPos();
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
