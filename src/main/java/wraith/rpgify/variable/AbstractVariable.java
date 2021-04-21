package wraith.rpgify.variable;

import com.sun.org.apache.xpath.internal.operations.Bool;
import wraith.rpgify.FunctionParser;
import wraith.rpgify.Utils;

import java.util.HashMap;

public abstract class AbstractVariable {

    protected Object value;
    protected String type;

    protected HashMap<String, Object> variables = new HashMap<>();

    public AbstractVariable(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public void setVariable(String variable, Object value) {
        this.variables.put(variable, value);
    }

    public void setVariables(HashMap<String, Object> variables) {
        this.variables = variables;
    }

    public Object getVariable(String variable) {
        return this.variables.get(variable);
    }

    public static AbstractVariable of(String s){
        if (s.equals("true") || s.equals("false")) {
            return new ImplementedVariable(Boolean.parseBoolean(s), "boolean");
        }
        else if (s.startsWith("\"") && s.endsWith("\"")) {
            return new ImplementedVariable(Utils.stripQuotations(s), "string");
        } else if (Utils.simpleFunctionCheck(s)) {
            return FunctionParser.getFunction(s, false);
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
            if ("this".equals(s)) {
                return new ImplementedVariable("this", "this");
            }
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
