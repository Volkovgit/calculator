package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InputHandler implements InputHandlerInterface {
    private final String NOT_ZERO_IN_ROME = "В римской системе счисления нет 0";
    private final String NEGATIVE_NUMBER_IN_ROME = "В римской системе счисления нет отрицательных чисел";
    private final String DIFFERENT_EXPRESSION_TYPE = "Используются разные системы счисления";
    private final String isMathRegularExpression = "^[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)\\s[\\+\\-\\/\\*]\\s[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)$";
    private final String isNegativeNumberRegularExpression = "^[\\-]([I|X|V|C|L|D|M]+|[\\d]+)$";
    private final String isRomeRegularExpression = "^[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+\\s[\\+\\-\\/\\*]\\s[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+$";
    private final String isArabicRegularExpression = "^[\\-]?[\\d]+\\s[\\+\\-\\/\\*]\\s[\\-]?[\\d]+$";
    private final String smallRomeNumbers = "[i|x|v|c|l|d|m]";
    private final List<String> OPERATORS = List.of("+", "-", "/", "*");
    private final Map<String, Integer> ROME_NUMBERS = Map.of(
            "I", 1,
            "II", 2,
            "III", 3,
            "IV", 4,
            "V", 5,
            "VI", 6,
            "VII", 7,
            "IIX", 8,
            "IX", 9,
            "X", 10);
    private final String ROME_TYPE = "ROME";
    private final String ARABIC_TYPE = "ARAB";
    private int x;
    private int y;
    private String action;
    private String expressionType;

    InputHandler() {
    }

    public String getResult(String expression) throws Exception {
        checkExpressionString(expression);
        action = getFilteredArrayFromInput(expression).get(1);
        setNumbers(expression);
        checkNumbersRange();
        float resultNumber = calculate();
        if (Objects.equals(expressionType, ROME_TYPE)) {
            checkRomeTypeResultOnZeroAndNegativeNumbers(resultNumber);
        }
        return convertResultArabToRome(resultNumber);
    }

    private void checkRomeTypeResultOnZeroAndNegativeNumbers(float result) throws Exception {
        if (result == 0) {
            throw new Exception(NOT_ZERO_IN_ROME);
        }
        if (result < 0) {
            throw new Exception(NEGATIVE_NUMBER_IN_ROME);
        }
    }


    private String convertResultArabToRome(float result) {
        if (Objects.equals(expressionType, ROME_TYPE)) {
            return convertArabNumberToRome(result);
        }
        return Float.toString(result);
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

    private float calculate() {
        Calculator calc = new Calculator();
        switch (action) {
            case ("-"):
                return calc.sub(x, y);
            case ("/"):
                return calc.div(x, y);
            case ("*"):
                return calc.mult(x, y);
            default:
                return calc.sum(x, y);
        }
    }


    private void setNumbers(String mathExpression) throws Exception {
        List<String> expressionArray = getFilteredArrayFromInput(mathExpression);
        if (Objects.equals(expressionType, ROME_TYPE)) {
            checkRomeNumbers(expressionArray.get(0), expressionArray.get(2));
            x = convertRomeToArabNumber(expressionArray.get(0));
            y = convertRomeToArabNumber(expressionArray.get(2));
        } else {
            x = Integer.parseInt(expressionArray.get(0));
            y = Integer.parseInt(expressionArray.get(2));
        }
    }

    /**
     * @param number1 - первое число; String
     * @param number2 - второе число; String
     * @throws Exception :
     *                   1) Если подряд идёт > 4 I (в любом из входящих чисел), то вернется исключение "Некоректно введено число : " + number1
     *                   2) Если любое и чисел написано в нижнем регистре, то вернется исключение "В римской системе буквы должны быть заглавными"
     */
    private void checkRomeNumbers(String number1, String number2) throws Exception {
        if (number1.matches(smallRomeNumbers) || number2.matches(smallRomeNumbers)) {
            throw new Exception("В римской системе буквы должны быть заглавными");
        }
        if (number1.matches("^IIII$")) throw new Exception("Некоректно введено число : " + number1);
        if (number2.matches("^IIII$")) throw new Exception("Некоректно введено число : " + number2);
    }

    private void checkNumbersRange() throws Exception {
        if ((x < 0 || x > 10) || (y < 0 || y > 10))
            throw new Exception("Числа должны быть в промежутке от 0 до 10");
        if (x == 0 && y == 0 && Objects.equals(action, "/")) throw new Exception("Нельзя делить на 0");
    }

    private Integer convertRomeToArabNumber(String romeNumber) throws Exception {
        int romeNumberLength = romeNumber.length() - 1;
        char[] charRomeNumber = romeNumber.toCharArray();
        int summ = 0;
        for (int i = romeNumberLength; i >= 0; i--) {
            int currentNumber = ROME_NUMBERS.get(charRomeNumber[i] + "");
            if (i - 1 >= 0) {
                int prevNumber = ROME_NUMBERS.get(charRomeNumber[i - 1] + "");
                if (currentNumber > prevNumber) {
                    summ += ROME_NUMBERS.get(charRomeNumber[i - 1] + "" + charRomeNumber[i]);
                    i--;
                } else {
                    summ += currentNumber;
                }
            } else {
                summ += currentNumber;
            }
        }
        return summ;
    }

    private String convertArabNumberToRome(float number) {
        return "I".repeat((int) number)
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }


    private List<String> getFilteredArrayFromInput(String str) {
        return List.of(Arrays.stream(str.split(" ")).filter((x) -> !x.isEmpty()).toArray(String[]::new));
    }

}
