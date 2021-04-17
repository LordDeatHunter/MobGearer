package wraith.rpgify.variable;

import java.util.ArrayList;
import java.util.HashSet;

public class SetVariable extends ImplementedVariable {

    public SetVariable() {
        super(null, "hashset");
        this.value = new HashSet<>();
    }

    public SetVariable(Object value) {
        super(null, "hashset");
        this.value = new HashSet<>();

        if (value instanceof String) {
            ArrayList<AbstractVariable> list = new ArrayList<>();
            ArrayVariable.getVariables((String) value, list);
            this.value = new HashSet<>(list);
        } else if (value instanceof AbstractVariable) {
            AbstractVariable variable = (AbstractVariable) value;
            if ("string".equals(variable.getType())) {
                ArrayList<AbstractVariable> list = new ArrayList<>();
                ArrayVariable.getVariables((String) variable.getValue(), list);
                this.value = new HashSet<>(list);
            } else if ("arraylist".equals(variable.getType())) {
                this.value = new HashSet<>((ArrayList<AbstractVariable>) variable.getValue());
            } else if ("hashset".equals(variable.getType())) {
                this.value = variable.getValue();
            }
        } else if (value instanceof ArrayList) {
            this.value = new HashSet<>((ArrayList<AbstractVariable>) value);
        }
    }

    public boolean contains(Object key) {
        return ((HashSet<AbstractVariable>) this.value).contains(key);
    }

}