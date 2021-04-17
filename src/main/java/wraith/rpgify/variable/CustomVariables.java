package wraith.rpgify.variable;

import org.yaml.snakeyaml.Yaml;
import wraith.rpgify.Config;
import wraith.rpgify.RPGify;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CustomVariables {

    private CustomVariables(){}

    private static final HashMap<String, AbstractVariable> VARIABLES = new HashMap<>();
    private static final HashMap<String, Region> REGIONS = new HashMap<>();

    public static AbstractVariable get(String s) {
        String[] var = s.split("\\.");
        if ("regions".equals(var[0])) {
            return REGIONS.getOrDefault(var[1], null);
        }
        return VARIABLES.getOrDefault(s, null);
    }

    public static void loadAll() {
        loadVariables();
        loadRegions();
        RPGify.LOGGER.info("All Custom Variables have been loaded.");
    }

    private static void loadVariables() {
        VARIABLES.clear();
        File file = new File("config/rpgify/variables.yaml");
        Yaml yaml = new Yaml();
        HashMap<String, Object> map = yaml.load(Config.readFile(file));
        for (Map.Entry<String, Object> region : map.entrySet()) {
            VARIABLES.put(region.getKey(), AbstractVariable.of(String.valueOf(region.getValue())));
        }
        RPGify.LOGGER.info("Custom Variables have been loaded.");
    }

    public static void loadRegions() {
        REGIONS.clear();
        File file = new File("config/rpgify/regions.yaml");
        Yaml yaml = new Yaml();
        HashMap<String, Object> map = yaml.load(Config.readFile(file));
        for (Map.Entry<String, Object> region : map.entrySet()) {
            REGIONS.put(region.getKey(), (Region) AbstractVariable.of(String.valueOf(region.getValue())).getValue());
        }
        RPGify.LOGGER.info("Custom Regions have been loaded.");
    }

}
