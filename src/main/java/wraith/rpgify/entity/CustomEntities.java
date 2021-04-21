package wraith.rpgify.entity;

import org.yaml.snakeyaml.Yaml;
import wraith.rpgify.Config;
import wraith.rpgify.FunctionParser;
import wraith.rpgify.RPGify;
import wraith.rpgify.function.Function;
import wraith.rpgify.roll.RollPicker;
import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.ConditionVariable;
import wraith.rpgify.variable.ImplementedVariable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomEntities {

    private CustomEntities(){}

    private static final HashMap<String, CustomEntity> ENTITIES = new HashMap<>();

    public static final HashMap<UUID, CustomEntity> INGAME_ENTITIES = new HashMap<>();

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

            if (!values.containsKey("mob_type")) {
                RPGify.LOGGER.error("Invalid Mob Group provided in " + filename);
            }
            AbstractVariable mobGroup = AbstractVariable.of(String.valueOf(values.get("mob_type")));

            if (!values.containsKey("spawn_condition")) {
                RPGify.LOGGER.error("Invalid Spawn Condition provided in " + filename);
            }
            ConditionVariable spawnCondition = new ConditionVariable(String.valueOf(values.get("spawn_condition")), true);

            String name = "";
            AbstractVariable displayName = AbstractVariable.of("false");
            if (values.containsKey("custom_name")) {
                name = String.valueOf(values.get("custom_name"));
                displayName = AbstractVariable.of(String.valueOf(values.getOrDefault("display_custom_name", "true")));
            }
            AbstractVariable maxHealth = AbstractVariable.of(String.valueOf(values.getOrDefault("max_health", "20")));
            AbstractVariable health;
            if (values.containsKey("health")) {
                health = AbstractVariable.of(String.valueOf(values.get("health")));
            } else {
                health = maxHealth;
            }

            RollPicker equipment = null;
            if (values.containsKey("equipment")) {
                equipment = RollPicker.fromYaml((Map<String, Object>) values.get("equipment"));
            }

            RollPicker drops = null;
            if (values.containsKey("drops")) {
                drops = RollPicker.fromYaml((Map<String, Object>) values.get("drops"));
            }

            AbstractVariable vanillaDrops = AbstractVariable.of(String.valueOf(values.getOrDefault("drop_vanilla_drops", "true")));

            AbstractVariable blockPos;
            if (values.containsKey("spawn_position")) {
                blockPos = AbstractVariable.of(String.valueOf(values.get("spawn_position")));
            } else {
                RPGify.LOGGER.error("Invalid Spawn Position provided in " + filename);
                return;
            }

            String entityID = filename.split("\\.")[0];

            AbstractVariable spawnEvent = new ImplementedVariable(null, "function");
            if (values.containsKey("spawn_event")) {
                HashMap<String, AbstractVariable> parameters = new HashMap<>();
                Function function = FunctionParser.getFunction("spawn_entity(entity:\"" + entityID + "\")", true);
                parameters.put("execute", function);
                spawnEvent = FunctionParser.getFunction(String.valueOf(values.get("spawn_event")), parameters, true);
            }
            ENTITIES.put(entityID, new CustomEntity(entityID, mobGroup, name, displayName, maxHealth, health, equipment, drops, blockPos, spawnEvent, vanillaDrops, spawnCondition));
        }
        RPGify.LOGGER.info("Custom Entities have been loaded.");
    }

    public static boolean entityExists(String entity) {
        return ENTITIES.containsKey(entity);
    }

}
