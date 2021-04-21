package wraith.rpgify;

import wraith.rpgify.function.Function;
import wraith.rpgify.variable.AbstractVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionParser {

    private FunctionParser(){}

    public static List<String> splitFunctions(String functionsString) {
        List<String> functions = new ArrayList<>();
        char[] chars = functionsString.toCharArray();

        StringBuilder s = new StringBuilder();
        int brackets = 0;
        boolean quotes = false;

        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];

            if (c == '\\' && i + 1 < chars.length && chars[i + 1] == '"') {
                ++i;
                s.append(chars[i]);
            } else if (c == '(' && !quotes) {
                ++brackets;
                s.append(c);
            } else if (c == ')' && !quotes) {
                --brackets;
                s.append(c);
            } else if (c == '"') {
                s.append(c);
                quotes = !quotes;
            } else if (c == ' ' && brackets == 0) {
                functions.add(s.toString());
                s = new StringBuilder();
            } else {
                s.append(c);
            }

            if (i + 1 >= chars.length) {
                functions.add(s.toString());
                s = new StringBuilder();
            }
        }
        return functions;
    }

    public static Function getFunction(String function, boolean override) {
        return getFunction(function, new HashMap<>(), override);
    }

    public static Function getFunction(String function, HashMap<String, AbstractVariable> parameters, boolean override) {
        String functionName = null;
        String s = "";
        boolean isInFunction = false;
        int brackets = 0;
        int squareBrackets = 0;
        int curlyBrackets = 0;
        boolean inQuotes = false;
        boolean isVariableName = true;
        String variable = null;

        char[] chars = function.toCharArray();
        for (int i = 0; i < chars.length - 1; ++i) {
            char c = chars[i];

            if (!isInFunction) {
                if (c == '(') {
                    functionName = s;
                    s = "";
                    isInFunction = true;
                } else {
                    s += c;
                }
            } else {
                if (isVariableName) {
                    if (c == ':') {
                        isVariableName = false;
                        variable = s;
                        s = "";
                    } else if (c == ' ') {
                        if (!"".equals(s)) {
                            s += '_';
                        }
                    } else {
                        s += c;
                    }
                } else {
                    if (c == '"') {
                        inQuotes = !inQuotes;
                        s += c;
                    } else if (c == ' ') {
                        if (inQuotes) {
                            s += c;
                        }
                    } else if (inQuotes && c == '\\' && i + 1 < chars.length - 1 && chars[i + 1] == '"') {
                        ++i;
                        s += chars[i];
                    } else if (c == '(' && !inQuotes) {
                        ++brackets;
                        s += c;
                    } else if (c == ')' && !inQuotes) {
                        --brackets;
                        s += c;
                    } else if (c == '[' && !inQuotes) {
                        ++squareBrackets;
                        s += c;
                    } else if (c == ']' && !inQuotes) {
                        --squareBrackets;
                        s += c;
                    } else if (c == '{' && !inQuotes) {
                        ++curlyBrackets;
                        s += c;
                    } else if (c == '}' && !inQuotes) {
                        --curlyBrackets;
                        s += c;
                    } else if (c == ',' && brackets == 0 && squareBrackets == 0 && curlyBrackets == 0) {
                        isVariableName = true;
                        parameters.put(variable, AbstractVariable.of(s));
                        s = "";
                        variable = null;
                    } else {
                        s += c;
                    }
                }
            }
            if (i + 1 >= chars.length - 1 && variable != null) {
                parameters.put(variable, AbstractVariable.of(s));
            }
        }
        return Function.factory(functionName == null ? "" : functionName, parameters, override);
    }

}
