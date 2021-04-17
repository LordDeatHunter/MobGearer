package wraith.rpgify.variable;

import wraith.rpgify.Utils;

import java.util.ArrayList;

public class ArrayVariable extends ImplementedVariable {

    public ArrayVariable(Object value) {
        super(null, "arraylist");

        this.value = new ArrayList<>();

        if (value instanceof String) {
            getVariables((String) value, (ArrayList<AbstractVariable>) this.value);
        } else if (value instanceof AbstractVariable) {
            AbstractVariable variable = (AbstractVariable) value;
            if ("string".equals(variable.getType())) {
                getVariables((String) variable.getValue(), (ArrayList<AbstractVariable>) this.value);
            } else if ("arraylist".equals(variable.getType())) {
                this.value = variable.getValue();
            }
        } else if (value instanceof ArrayList) {
            this.value = value;
        }
    }

    public static void getVariables(String s, ArrayList<AbstractVariable> list) {
        char[] chars = s.substring(1, s.length() - 1).toCharArray();
        boolean inQuotes = false;
        StringBuilder var = new StringBuilder();
        int brackets = 0;
        int squareBrackets = 0;
        int curlyBrackets = 0;

        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (c == '"') {
                inQuotes = !inQuotes;
                var.append(c);
            } else if (c == ' ') {
                if (inQuotes) {
                    var.append(c);
                }
            } else if (inQuotes && c == '\\' && i + 1 < chars.length - 1 && chars[i + 1] == '"') {
                ++i;
                var.append(chars[i]);
            } else if (c == '(' && !inQuotes) {
                ++brackets;
                var.append(c);
            } else if (c == ')' && !inQuotes) {
                --brackets;
                var.append(c);
            } else if (c == '[' && !inQuotes) {
                ++squareBrackets;
                var.append(c);
            } else if (c == ']' && !inQuotes) {
                --squareBrackets;
                var.append(c);
            } else if (c == '{' && !inQuotes) {
                ++curlyBrackets;
                var.append(c);
            } else if (c == '}' && !inQuotes) {
                --curlyBrackets;
                var.append(c);
            } else if (c == ',' && brackets == 0 && squareBrackets == 0 && curlyBrackets == 0) {
                list.add(AbstractVariable.of(var.toString()));
                var = new StringBuilder();
            } else {
                var.append(c);
            }
        }
        if (!"".equals(var.toString())) {
            list.add(AbstractVariable.of(var.toString()));
        }
    }

    public AbstractVariable getRandomValue() {
        ArrayList<AbstractVariable> list = (ArrayList<AbstractVariable>) this.value;
        if (list.isEmpty()) {
            return null;
        }
        return list.get(Utils.getRandomIntInRange(0, list.size() - 1));
    }

    public Object getValue(int i) {
        ArrayList<AbstractVariable> list = (ArrayList<AbstractVariable>) this.value;
        if (i >= list.size()) {
            return null;
        }
        return list.get(i);
    }

}
