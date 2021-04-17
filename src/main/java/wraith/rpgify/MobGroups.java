package wraith.rpgify;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MobGroups {

    private static final HashMap<String, HashSet<String>> MOB_GROUPS = new HashMap<>();

    public static void loadMobGroups() {
        MOB_GROUPS.clear();
        Yaml yaml = new Yaml();
        HashMap<String, Object> values = yaml.load(Config.readFile(new File("config/rpgify/mobs.yaml")));
        for (Map.Entry<String, Object> value : values.entrySet()) {
            MOB_GROUPS.put(value.getKey(), new HashSet<>((ArrayList<String>) value.getValue()));
        }
    }

    public static Object get(String group) {
        return MOB_GROUPS.get(group);
    }


}
