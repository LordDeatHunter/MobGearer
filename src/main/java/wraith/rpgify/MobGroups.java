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
            String group = value.getKey();
            MOB_GROUPS.put(group, new HashSet<>());
            ArrayList<String> mobs = (ArrayList<String>) value.getValue();
            for (String mob : mobs) {
                if (mob.contains(":")) {
                    MOB_GROUPS.get(group).add(mob);
                } else if (MOB_GROUPS.containsKey(mob)) {
                    MOB_GROUPS.get(group).addAll(MOB_GROUPS.get(mob));
                }
            }
        }
    }

    public static Object get(String group) {
        return MOB_GROUPS.getOrDefault(group, null);
    }


}
