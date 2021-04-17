package wraith.rpgify;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DimensionGroups {

    private static final HashMap<String, HashSet<String>> DIMENSIONS = new HashMap<>();

    public static void loadDimensionGroups() {
        DIMENSIONS.clear();
        Yaml yaml = new Yaml();
        HashMap<String, Object> values = yaml.load(Config.readFile(new File("config/rpgify/dimensions.yaml")));
        for (Map.Entry<String, Object> value : values.entrySet()) {
            DIMENSIONS.put(value.getKey(), new HashSet<>((ArrayList<String>) value.getValue()));
        }
        RPGify.LOGGER.info("Dimension Groups have been loaded.");
    }

    public static void addDimensionsToGroup(String dimensionGroup, HashSet<String> dimensions) {
        DIMENSIONS.get(dimensionGroup).addAll(dimensions);
    }

    public static void removeDimensionFrom(String dimensionGroup, String dimension) {
        DIMENSIONS.get(dimensionGroup).remove(dimension);
    }

    public static void addDimensions(String dimensionGroup, HashSet<String> dimensions, boolean overwrite) {
        if (!DIMENSIONS.containsKey(dimensionGroup) || overwrite) {
            DIMENSIONS.put(dimensionGroup, dimensions);
        }
    }

}
