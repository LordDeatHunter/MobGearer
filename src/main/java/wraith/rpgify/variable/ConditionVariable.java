package wraith.rpgify.variable;

import com.udojava.evalex.Expression;
import wraith.rpgify.FunctionParser;
import wraith.rpgify.Utils;
import wraith.rpgify.function.Function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConditionVariable extends ImplementedVariable {

    private final boolean override;
    private final HashMap<String, AbstractVariable> map;
    private List<String> functions;

    public ConditionVariable(String functionsString, boolean override) {
        super(true, "boolean");
        this.functions = new ArrayList<>();
        if (!"none".equals(functionsString)) {
            this.functions = FunctionParser.splitFunctions(functionsString);
        }
        map = new HashMap<>();
        this.override = override;
    }

    @Override
    public Object getValue() {
        String condition = "";
        boolean isNotStatement = false;
        if (!functions.isEmpty()) {
            for (String functionString : functions) {
                if (!Utils.simpleFunctionCheck(functionString)) {
                    if ("!".equals(functionString) || "not".equals(functionString)) {
                        condition += ("not(");
                        isNotStatement = true;
                    } else {
                        condition += functionString + " ";
                        if (isNotStatement) {
                            isNotStatement = false;
                            condition += ") ";
                        }
                    }
                } else {
                    Function function = FunctionParser.getFunction(functionString, map, override);
                    function.setVariables(this.variables);
                    condition += ((boolean) function.getValue()) + " ";
                    if (isNotStatement) {
                        isNotStatement = false;
                        condition += ") ";
                    }
                }
            }
            condition = condition.replace("and", "&&");
            condition = condition.replace("or", "||");
            condition = condition.replace("is", "==");
            Expression expression = new Expression(condition.substring(0, condition.length() - 1));
            return expression.eval().floatValue() != 0;
        }
        return true;
    }

}
