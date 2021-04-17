package wraith.rpgify.variable;

import java.util.HashMap;

public class MapVariable extends ImplementedVariable {

    public MapVariable() {
        super(null, "hashmap");
        this.value = new HashMap<>();
    }

    public MapVariable(Object value) {
        super(null, "hashmap");

        this.value = new HashMap<>();

        if (value instanceof String) {
            getVariables((String) value);
        } else if (value instanceof AbstractVariable) {
            AbstractVariable variable = (AbstractVariable) value;
            if ("string".equals(variable.getType())) {
                getVariables((String) variable.getValue());
            } else if ("hashmap".equals(variable.getType())) {
                this.value = variable.getValue();

            }
            this.value = ((AbstractVariable) value).getValue();
        } else if (value instanceof HashMap) {
            this.value = value;
        }
    }

    private void getVariables(String s) {
        char[] chars = s.substring(1, s.length() - 1).toCharArray();
        boolean inQuotes = false;
        String var = "";
        String variable = "";
        int brackets = 0;
        int squareBrackets = 0;
        int curlyBrackets = 0;
        boolean isVariableName = true;

        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (isVariableName) {
                if (c == ':') {
                    isVariableName = false;
                    variable = var;
                    var = "";
                } else if (c == ' ') {
                    if (!"".equals(var)) {
                        var += '_';
                    }
                } else {
                    var += c;
                }
            } else {
                if (c == '"') {
                    inQuotes = !inQuotes;
                    var += c;
                } else if (c == ' ') {
                    if (inQuotes) {
                        var += c;
                    }
                } else if (inQuotes && c == '\\' && i + 1 < chars.length - 1 && chars[i + 1] == '"') {
                    ++i;
                    var += chars[i];
                } else if (c == '(' && !inQuotes) {
                    ++brackets;
                    var += c;
                } else if (c == ')' && !inQuotes) {
                    --brackets;
                    var += c;
                } else if (c == '[' && !inQuotes) {
                    ++squareBrackets;
                    var += c;
                } else if (c == ']' && !inQuotes) {
                    --squareBrackets;
                    var += c;
                } else if (c == '{' && !inQuotes) {
                    ++curlyBrackets;
                    var += c;
                } else if (c == '}' && !inQuotes) {
                    --curlyBrackets;
                    var += c;
                } else if (c == ',' && brackets == 0 && squareBrackets == 0 && curlyBrackets == 0) {
                    isVariableName = true;
                    ((HashMap<String, AbstractVariable>)this.value).put(variable, AbstractVariable.of(var));
                    var = "";
                    variable = null;
                } else {
                    var += c;
                }
            }
            if (i + 1 >= chars.length - 1 && variable != null) {
                ((HashMap<String, AbstractVariable>)this.value).put(variable, AbstractVariable.of(var));
            }
        }
    }


    public Object getValue(Object key) {
        return ((HashMap<String, AbstractVariable>)this.value).getOrDefault(key, null);
    }

}
