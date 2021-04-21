package wraith.rpgify.function;

import net.minecraft.entity.player.PlayerEntity;
import wraith.rpgify.event.PlayerEnterRegionEvent;
import wraith.rpgify.event.listener.PlayerEnterRegionListener;
import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.Region;

import java.util.HashMap;
import java.util.HashSet;

public class OnPlayerEnterRegionFunction extends Function implements PlayerEnterRegionListener {

    public static HashMap<String, HashSet<String>> mandatoryParameters = new HashMap<String, HashSet<String>>(){{
        put("region", new HashSet<String>(){{
            add("region");
        }});
        put("execute", new HashSet<String>(){{
            add("function");
        }});
    }};
    private static HashMap<String, HashSet<String>> optionalParameters = new HashMap<String, HashSet<String>>() {{
        put("enter_or_leave", new HashSet<String>(){{
            add("string");
        }});
        put("player", new HashSet<String>(){{
            add("string");
        }});
    }};

    public OnPlayerEnterRegionFunction(String functionName, HashMap<String, AbstractVariable> parameters) {
        super(functionName, parameters);
        PlayerEnterRegionEvent.getInstance().addListener(this);
    }

    @Override
    public void onEnter(PlayerEntity player, Region region) {
        if (this.parameters.containsKey("enter_or_leave") && "leave".equals(String.valueOf(this.parameters.get("enter_or_leave").getValue())) || !this.parameters.containsKey("execute")) {
            return;
        }
        if (!this.parameters.containsKey("region") || !this.parameters.get("region").getValue().equals(region)) {
            return;
        }
        if (this.parameters.containsKey("player") && !this.parameters.get("player").equals(player.getName().toString())) {
            return;
        }
        this.parameters.get("execute").setVariable("player", player);
        this.parameters.get("execute").setVariable("region", region);
        this.parameters.get("execute").getValue();
    }

    @Override
    public void onLeave(PlayerEntity player, Region region) {
        if (!this.parameters.containsKey("enter_or_leave") || "enter".equals(String.valueOf(this.parameters.get("enter_or_leave").getValue())) || !this.parameters.containsKey("execute")) {
            return;
        }
        if (!this.parameters.containsKey("region") || !this.parameters.get("region").getValue().equals(region)) {
            return;
        }
        if (this.parameters.containsKey("player") && !this.parameters.get("player").equals(player.getName().toString())) {
            return;
        }
        this.parameters.get("execute").setVariable("player", player);
        this.parameters.get("execute").setVariable("region", region);
        this.parameters.get("execute").getValue();
    }

}
