package org.example.InputHandler;

import org.example.Calculator.Calculator;
import org.example.Numbers.ArabNumbers;
import org.example.Numbers.RomeNumbers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InputHandler implements InputHandlerInterface {
    private final String DIFFERENT_EXPRESSION_TYPE = "Используются разные системы счисления";
    private final String isMathRegularExpression = "^[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)\\s[\\+\\-\\/\\*]\\s[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)$";
    private final String isNegativeNumberRegularExpression = "^[\\-]([I|X|V|C|L|D|M]+|[\\d]+)$";
    private final String isRomeRegularExpression = "^[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+\\s[\\+\\-\\/\\*]\\s[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+$";
    private final String isArabicRegularExpression = "^[\\-]?[\\d]+\\s[\\+\\-\\/\\*]\\s[\\-]?[\\d]+$";
    private final List<String> OPERATORS = List.of("+", "-", "/", "*");
    private final String ROME_TYPE = "ROME";
    private final String ARABIC_TYPE = "ARAB";
    private String action;
    private String expressionType;

    public InputHandler() {
    }

    public String getResult(String expression) throws Exception {
        checkExpressionString(expression);
        action = getFilteredArrayFromInput(expression).get(1);

        List<Integer> numbersArray = getNumbers(expression);
        checkNumbersRange(numbersArray.get(0),numbersArray.get(1));

        float resultNumber = calculate(numbersArray.get(0),numbersArray.get(1));
        if (Objects.equals(expressionType, ROME_TYPE)) {
            checkRomeTypeResultOnZeroAndNegativeNumbers(resultNumber);
            return convertArabNumberToRome(resultNumber);
        }
        else{
            if(!Objects.equals(action,"/")){
                return Integer.toString((int)resultNumber);
            }
            return Float.toString(resultNumber);
        }
    }



    private void checkExpressionString(String mathExpression) throws Exception {
        List<String> expressionArray = getFilteredArrayFromInput(mathExpression);
        if (!mathExpression.matches(isMathRegularExpression))
            throw new Exception("Некорректный формат математической операции");
        setExpressionType(mathExpression);
        if (!OPERATORS.contains(expressionArray.get(1))) throw new Exception("Некоректное математическое действие");
        if (expressionArray.get(0).matches(isNegativeNumberRegularExpression) || expressionArray.get(2).matches(isNegativeNumberRegularExpression)) {
            if (Objects.equals(expressionType, ROME_TYPE))
                throw new Exception("В римской системе счисления нет отрицательных чисел");
            throw new Exception("Ошибка : отрицательные числа запрещены");
        }
    }
    private void setExpressionType(String mathExpression) throws Exception {
        boolean isRomeExpression = mathExpression.matches(isRomeRegularExpression);
        boolean isArabicExpression = mathExpression.matches(isArabicRegularExpression);
        if (isRomeExpression && isArabicExpression) throw new Exception(DIFFERENT_EXPRESSION_TYPE);
        if (isArabicExpression) {
            expressionType = ARABIC_TYPE;
        } else if (isRomeExpression) {
            expressionType = ROME_TYPE;
        }
    }

    private float calculate(int firstNumber,int secondNumber) {
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
    private List<Integer> getNumbers(String mathExpression) throws Exception {
        List<String> expressionArray = getFilteredArrayFromInput(mathExpression);
        if (Objects.equals(expressionType, ROME_TYPE)) {
            return new RomeNumbers(expressionArray.get(0), expressionArray.get(2)).getNumbers();
        } else {
            return new ArabNumbers(expressionArray.get(0), expressionArray.get(2)).getNumbers();
        }
    }
    private void checkNumbersRange(int numberOne, int numberTwo) throws Exception {
        if ((numberOne < 0 || numberOne > 10) || (numberTwo < 0 || numberTwo > 10))
            throw new Exception("Числа должны быть в промежутке от 0 до 10");
        if (numberOne == 0 && numberTwo == 0 && Objects.equals(action, "/")) throw new Exception("Нельзя делить на 0");
    }
    private List<String> getFilteredArrayFromInput(String str) {
        return List.of(
                Arrays.stream(str.split(" "))
                .filter((x) -> !x.isEmpty())
                        .toArray(String[]::new));
    }

}
