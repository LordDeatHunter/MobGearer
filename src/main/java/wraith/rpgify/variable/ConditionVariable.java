package wraith.rpgify.variable;

import com.udojava.evalex.Expression;
import wraith.rpgify.FunctionParser;
import wraith.rpgify.Utils;

import java.util.ArrayList;
import java.util.List;

public class ConditionVariable extends ImplementedVariable {

    private List<String> functions;

    public ConditionVariable(String functionsString) {
        super(true, "boolean");
        this.functions = new ArrayList<>();
        if (!"none".equals(functionsString)) {
            this.functions = FunctionParser.splitFunctions(functionsString);
        }
    }

    @Override
    public Object getValue() {
        StringBuilder condition = new StringBuilder();
        if (!functions.isEmpty()) {
            for (String function : functions) {
                if (!Utils.simpleFunctionCheck(function)) {
                    condition.append(function);
                } else {
                    condition.append((boolean) FunctionParser.getFunction(function).getValue());
                }
            }
            condition.toString().replace("and", "&&");
            condition.toString().replace("or", "||");
            condition.toString().replace("not", "!");
            condition.toString().replace("is", "==");
            Expression expression = new Expression(condition.toString());
            return expression.eval().floatValue() != 0;
        }
        return true;
    }

}
