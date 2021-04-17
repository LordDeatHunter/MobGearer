package wraith.rpgify.roll;

import wraith.rpgify.variable.AbstractVariable;
import wraith.rpgify.variable.ConditionVariable;

import java.util.HashMap;
import java.util.Map;

public class Rollable {

    private final AbstractVariable weight;
    private final AbstractVariable condition;
    private final boolean removeIfChosen;
    private HashMap<String, AbstractVariable> content;

    protected Rollable(AbstractVariable weight, AbstractVariable condition, boolean removeIfChosen, HashMap<String, AbstractVariable> content) {
        this.weight = weight;
        this.condition = condition;
        this.removeIfChosen = removeIfChosen;
        this.content = content;
    }

    public boolean shouldRemoveIfChosen() {
        return this.removeIfChosen;
    }

    public float getWeight(){
        return Float.parseFloat(String.valueOf(this.weight.getValue()));
    }

    public HashMap<String, AbstractVariable> getContent() {
        return this.content;
    }

    public boolean isConditionSatisfied() {
        return Boolean.parseBoolean(String.valueOf(this.condition.getValue()));
    }

    public static Rollable fromYaml(HashMap<String, Object> map) {
        boolean remove = (boolean) map.getOrDefault("remove_from_pool_if_chosen", false);
        AbstractVariable weight = AbstractVariable.of(String.valueOf(map.getOrDefault("weight", "0.0")));
        ConditionVariable condition = new ConditionVariable(String.valueOf(map.getOrDefault("condition", "none")));

        HashMap<String, Object> values = (HashMap<String, Object>) map.get("contents");

        HashMap<String, AbstractVariable> contents = new HashMap<>();

        if (values.containsKey("entries")) {
            contents.put("entries", RollPicker.fromYaml(values));
        } else {
            for (Map.Entry<String, Object> value : values.entrySet()) {
                contents.put(value.getKey(), AbstractVariable.of((String) value.getValue()));
            }
        }
        return new Rollable(weight, condition, remove, contents);
    }

}
