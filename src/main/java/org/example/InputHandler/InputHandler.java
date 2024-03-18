package org.example.InputHandler;

import org.example.Calculator.Calculator;
import org.example.Numbers.ArabNumbers;
import org.example.Numbers.Numbers;
import org.example.Numbers.RomeNumbers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InputHandler implements InputHandlerInterface {

    private final String INCORRECT_MATH_EXPRESSION_FORMAT = "Некорректный формат математической операции";
    private final String INCORRECT_MATH_OPERATOR = "Некоректное математическое действие";
    private final String isMathRegularExpression = "^[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)\\s[\\+\\-\\/\\*]\\s[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)$";
    private final List<String> OPERATORS = List.of("+", "-", "/", "*");

    private String action;

    public InputHandler() {
    }

    public String getResult(String expression) throws Exception {
        checkExpressionString(expression);
        action = getFilteredArrayFromInput(expression).get(1);

        Numbers numbers = new Numbers(getFilteredArrayFromInput(expression));
        List<Integer> numbersArray = numbers.getNumbers();
        if (numbersArray.get(0) == 0 && numbersArray.get(1) == 0 && Objects.equals(action, "/")) throw new Exception("Нельзя делить на 0");

        float resultNumber = calculate(numbersArray.get(0), numbersArray.get(1));
        return numbers.convertResult(resultNumber,action);
    }


    private void checkExpressionString(String mathExpression) throws Exception {
        List<String> expressionArray = getFilteredArrayFromInput(mathExpression);
        if (!mathExpression.matches(isMathRegularExpression))
            throw new Exception(INCORRECT_MATH_EXPRESSION_FORMAT);
        if (!OPERATORS.contains(expressionArray.get(1))) throw new Exception(INCORRECT_MATH_OPERATOR);
    }

    private float calculate(int firstNumber, int secondNumber) {
        Calculator calc = new Calculator();
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
