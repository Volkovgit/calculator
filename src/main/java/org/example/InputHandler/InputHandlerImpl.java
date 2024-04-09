package org.example.InputHandler;

import org.example.Calculator.CalcularotImpl;
import org.example.Numbers.NumbersImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InputHandlerImpl implements InputHandler {

    private final String INCORRECT_MATH_EXPRESSION_FORMAT = "Некорректный формат математической операции";
    private final String INCORRECT_MATH_OPERATOR = "Некоректное математическое действие";
    private final String MATH_REGULAR_EXPRESSION = "^[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)\\s[\\+\\-\\/\\*]\\s[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)$";
    private final List<String> OPERATORS = List.of("+", "-", "/", "*");

    public InputHandlerImpl() {
    }

    public String getResult(String expression) throws Exception {
        checkExpressionString(expression);
        String action = getFilteredArrayFromInput(expression).get(1);

        NumbersImpl numbers = new NumbersImpl(getFilteredArrayFromInput(expression));
        List<Integer> numbersArray = numbers.getNumbers();
        if (numbersArray.get(0) == 0 && numbersArray.get(1) == 0 && Objects.equals(action, "/")) {
            throw new Exception("Нельзя делить на 0");
        }

        float resultNumber = calculate(numbersArray.get(0), numbersArray.get(1), action);
        return numbers.convertResult(resultNumber, action);
    }


    private void checkExpressionString(String mathExpression) throws Exception {
        List<String> expressionArray = getFilteredArrayFromInput(mathExpression);
        if (!mathExpression.matches(MATH_REGULAR_EXPRESSION)) {
            throw new Exception(INCORRECT_MATH_EXPRESSION_FORMAT);
        }
        if (!OPERATORS.contains(expressionArray.get(1))) {
            throw new Exception(INCORRECT_MATH_OPERATOR);
        }
    }

    private float calculate(int firstNumber, int secondNumber, String action) {
        CalcularotImpl calc = new CalcularotImpl();
        switch (action) {
            case ("-"):
                return calc.sub(firstNumber, secondNumber);
            case ("/"):
                return calc.div(firstNumber, secondNumber);
            case ("*"):
                return calc.mult(firstNumber, secondNumber);
            default:
                return calc.sum(firstNumber, secondNumber);
        }
    }


    private List<String> getFilteredArrayFromInput(String str) {
        return List.of(
                Arrays.stream(str.split(" "))
                        .filter((x) -> !x.isEmpty())
                        .toArray(String[]::new));
    }

}
