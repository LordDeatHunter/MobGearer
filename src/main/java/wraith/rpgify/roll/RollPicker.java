package wraith.rpgify.roll;

import wraith.rpgify.Utils;
import wraith.rpgify.function.RandomChanceFunction;
import wraith.rpgify.variable.AbstractVariable;

import java.util.*;

public class RollPicker extends AbstractVariable {

    private static final HashSet<String> SUCCESS_ROLL_TYPE = new HashSet<String>(){{
        add("success");
        add("succeed");
    }};

    private final String rollType;
    private final AbstractVariable rollAmount;
    private final List<Rollable> rollables;
    private final float emptyRollChance;
    private List<Rollable> currentRollables;
    private float totalChance = 0;
    private float currentTotalChance = 0;

    public RollPicker(String rollType, AbstractVariable rollAmount, float emptyRollChance, List<Rollable> rollables) {
        super(null, null);
        this.rollType = rollType;
        this.rollAmount = rollAmount;
        this.rollables = rollables;
        this.emptyRollChance = emptyRollChance;
        for (Rollable entry : rollables) {
            totalChance += entry.getWeight();
        }
    }

    public List<HashMap<String, AbstractVariable>> getEntries() {
        this.currentTotalChance = totalChance;
        currentRollables = new ArrayList<>(rollables);
        ArrayList<HashMap<String, AbstractVariable>> entries = new ArrayList<>();
        int rolls = (int) Float.parseFloat(String.valueOf(rollAmount.getValue()));
        for (int i = 0; i < rolls; ++i) {

            if (RandomChanceFunction.getRandomChance(this.emptyRollChance)) {
                continue;
            }

            HashMap<String, AbstractVariable> entry = getEntry();
            if (entry != null) {
                entries.add(entry);
            }
        }
        return entries;
    }

    public HashMap<String, AbstractVariable> getEntry() {
        Rollable entry = null;
        HashMap<String, AbstractVariable> returnable = new HashMap<>();
        int i;
        do {
            float chance = Utils.getRandomFloatInRange(0, currentTotalChance);
            float totalChance = 0;
            i = 0;
            for (Rollable rollable : currentRollables) {
                float rollChance = rollable.getWeight();
                ++i;
                if (totalChance <= chance && chance <= totalChance + rollChance && rollable.isConditionSatisfied()) {
                    entry = rollable;
                    returnable = entry.getContent();
                    break;
                }
                totalChance += rollChance;
            }
        } while(SUCCESS_ROLL_TYPE.contains(rollType.toLowerCase()) && entry == null);

        if (entry != null && entry.shouldRemoveIfChosen()) {
            currentTotalChance -= entry.getWeight();
            currentRollables.remove(i - 1);
        }

        return returnable;
    }

    public static RollPicker fromYaml(Map<String, Object> map) {
        String rollType = (String) map.getOrDefault("roll_type", "attempt");
        float emptyRollChance = Float.parseFloat(String.valueOf(map.getOrDefault("chance_for_empty_roll", 0f)));
        AbstractVariable rolls = AbstractVariable.of(String.valueOf(map.getOrDefault("rolls", "1")));
        ArrayList<HashMap<String, Object>> entries = (ArrayList<HashMap<String, Object>>) map.get("entries");
        ArrayList<Rollable> rollables = new ArrayList<>();
        for (HashMap<String, Object> entry : entries) {
            rollables.add(Rollable.fromYaml(entry));
        }
        return new RollPicker(rollType, rolls, emptyRollChance, rollables);
    }

    public String getRollType() {
        return this.rollType;
    }

    public AbstractVariable getRollAmount() {
        return this.rollAmount;
    }

    public List<Rollable> getRollables() {
        return this.rollables;
    }

    @Override
    public Object getValue() {
        List<HashMap<String, AbstractVariable>> entries = getEntries();

         ArrayList<Object> values = new ArrayList<>();

        for (HashMap<String, AbstractVariable> entry : entries) {
            //If nested
            if (entry.containsKey("entries")) {
                values.addAll((ArrayList<Object>)entry.get("entries").getValue());
            } else {
                for (Map.Entry<String, AbstractVariable> e : entry.entrySet()) {
                    values.add(e.getValue().getValue());
                }
            }
        }
        return values;
    }


}