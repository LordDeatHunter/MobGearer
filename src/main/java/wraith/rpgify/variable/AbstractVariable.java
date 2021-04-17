package wraith.rpgify.variable;

import wraith.rpgify.FunctionParser;
import wraith.rpgify.Utils;

public abstract class AbstractVariable {

    protected Object value;
    protected String type;

    public AbstractVariable(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public static AbstractVariable of(String s){
        if (s.startsWith("\"") && s.endsWith("\"")) {
            return new ImplementedVariable(Utils.stripQuotations(s), "string");
        } else if (Utils.simpleFunctionCheck(s)) {
            return FunctionParser.getFunction(s);
        } else if (s.startsWith("[") && s.endsWith("]")) {
            return new ArrayVariable(s);
        } else if (s.startsWith("{") && s.endsWith("}")) {
            return new MapVariable(s);
        } else {
            try {
                int val = Integer.parseInt(s);
                return new ImplementedVariable(val, "integer");
            } catch (Exception ignored) {}
            try {
                float val = Float.parseFloat(s);
                return new ImplementedVariable(val, "float");
            } catch (Exception ignored) {}
            return CustomVariables.get(s);
        }
    }

    public Object getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

    public void setValue(Object value) {
        if (value.getClass().getSimpleName().equals(type)) {
            this.value = value;
        }
    }

}
