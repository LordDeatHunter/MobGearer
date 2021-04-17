package wraith.rpgify.entity;

import org.yaml.snakeyaml.Yaml;
import wraith.rpgify.Config;
import wraith.rpgify.RPGify;
import wraith.rpgify.roll.RollPicker;
import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.ConditionVariable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CustomEntities {

    private CustomEntities(){}

    private static final HashMap<String, CustomEntity> ENTITIES = new HashMap<>();

    public static CustomEntity getEntity(String entity) {
        return ENTITIES.getOrDefault(entity, null);
    }

    public static void loadEntities() {
        ENTITIES.clear();
        File[] files = Config.getFiles("config/rpgify/mobs/");
        for (File file : files) {
            Yaml yaml = new Yaml();

            Map<String, Object> values = yaml.load(Config.readFile(file));

            String filename = file.getName();

            if (!values.containsKey("mob_group")) {
                RPGify.LOGGER.error("Invalid Mob Group provided in " + filename);
            }
            String mobGroup = (String) values.get("mobGroup");

            if (!values.containsKey("spawn_condition")) {
                RPGify.LOGGER.error("Invalid Spawn Condition provided in " + filename);
            }
            ConditionVariable spawnCondition = new ConditionVariable((String) values.get("spawn_condition"));


            AbstractVariable name = AbstractVariable.of(String.valueOf(values.getOrDefault("custom_name", "")));
            AbstractVariable displayName = AbstractVariable.of(String.valueOf(values.getOrDefault("display_custom_name", "true")));
            AbstractVariable maxHealth = AbstractVariable.of(String.valueOf(values.getOrDefault("max_health", "20")));
            AbstractVariable health = AbstractVariable.of(String.valueOf(values.getOrDefault("health", "20")));

            RollPicker equipment = null;
            if (values.containsKey("equipment")) {
                equipment = RollPicker.fromYaml((Map<String, Object>) values.get("equipment"));
            }

            RollPicker drops = null;
            if (values.containsKey("drops")) {
                drops = RollPicker.fromYaml((Map<String, Object>) values.get("drops"));
            }

            AbstractVariable vanillaDrops = AbstractVariable.of(String.valueOf(values.getOrDefault("drop_vanilla_drops", "true")));

            ENTITIES.put(filename.split("\\.")[0], new CustomEntity(mobGroup, name, displayName, maxHealth, health, equipment, drops, vanillaDrops, spawnCondition));
        }
        RPGify.LOGGER.info("Custom Entities have been loaded.");
    }

    public static boolean entityExists(String entity) {
        return ENTITIES.containsKey(entity);
    }

}
