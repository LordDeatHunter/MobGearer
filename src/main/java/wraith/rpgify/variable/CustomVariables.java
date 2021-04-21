package wraith.rpgify.variable;

import org.yaml.snakeyaml.Yaml;
import wraith.rpgify.Config;
import wraith.rpgify.MobGroups;
import wraith.rpgify.RPGify;
import wraith.rpgify.entity.CustomEntities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CustomVariables {

    private CustomVariables(){}

    private static final HashMap<String, AbstractVariable> VARIABLES = new HashMap<>();
    private static final HashMap<String, Region> REGIONS = new HashMap<>();

    public static AbstractVariable get(String s) {
        String[] var = s.split("\\.");
        String varType = var[0];
        if (var.length > 1) {
            if ("regions".equals(varType)) {
                return REGIONS.getOrDefault(var[1], null);
            } else if ("entities".equals(varType)) {
                return CustomEntities.getEntity(var[1]);
            } else if ("mobs".equals(varType)) {
                return new SetVariable(MobGroups.get(var[1]));
            }
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

    public static HashMap<String, Region> getRegions() {
        return REGIONS;
    }
}
