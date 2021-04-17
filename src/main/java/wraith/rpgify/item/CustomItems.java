package wraith.rpgify.item;

import org.yaml.snakeyaml.Yaml;
import wraith.rpgify.Config;
import wraith.rpgify.RPGify;
import wraith.rpgify.roll.RollPicker;
import wraith.rpgify.variable.AbstractVariable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomItems {

    private CustomItems(){}

    private static final HashMap<String, CustomItem> ITEMS = new HashMap<>();

    public static CustomItem getItem(String item) {
        return ITEMS.getOrDefault(item, null);
    }

    public static void loadItems() {
        ITEMS.clear();
        File[] files = Config.getFiles("config/rpgify/items/");
        for (File file : files) {
            Yaml yaml = new Yaml();

            Map<String, Object> values = yaml.load(Config.readFile(file));

            String name = file.getName();
            if (!values.containsKey("item")) {
                RPGify.LOGGER.error("Invalid item provided in " + name);
            }
            String itemName = null;
            if (values.containsKey("name")) {
                itemName = (String) values.get("name");
            }
            AbstractVariable itemId;
            if (values.get("item") instanceof Map) {
                itemId = RollPicker.fromYaml((Map<String, Object>)values.get("item"));
            } else {
                itemId = AbstractVariable.of((String)values.get("item"));
            }

            ArrayList<String> lore = new ArrayList<>();

            if (values.containsKey("lore")) {
                try {
                    lore = (ArrayList<String>) values.get("lore");
                } catch (Exception e) {
                    RPGify.LOGGER.error("Invalid lore line provided in " + name);
                }
            }
            RollPicker enchantments = null;
            if (values.containsKey("enchantments")) {
                enchantments = RollPicker.fromYaml((Map<String, Object>) values.get("enchantments"));
            }

            ITEMS.put(name.split("\\.")[0], new CustomItem(itemId, itemName, lore, enchantments));
        }
        RPGify.LOGGER.info("Custom Items have been loaded.");
    }

    public static boolean itemExists(String item) {
        return ITEMS.containsKey(item);
    }

}
