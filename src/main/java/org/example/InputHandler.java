package org.example;

import java.util.*;

public class InputHandler {

    private final List<String> operators = Arrays.asList("+", "-", "/", "*");
    private final Map<String, Integer> romeNumbers = createRomeNumbersMap();
    private final String romeType = "rome";
    private final String arabicType = "arab";
    private int x;
    private int y;
    private String action;
    private String expressionType;

    InputHandler() {

    }

    public String getResult(String expression) {
        try {
            checkExpressionString(expression);
            action = getFilteredArrayFromInput(expression)[1];
            setNumbers(expression);
            float result = calculate();
            checkResult(result);
            return convertResult(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return "";
    }

    private void checkResult(float result) throws Exception {
        if (Objects.equals(expressionType, romeType)) {
            if (result == 0) throw new Exception("В римской системе счисления нет 0");
            if (result < 0) throw new Exception("В римской системе счисления нет отрицательных чисел");
        }
    }


    private String convertResult(float result) {
        if (Objects.equals(expressionType, romeType)) {
            return convertArabNumberToRome(result);
        }
        return Float.toString(result);
    }


    private void checkExpressionString(String mathExpression) throws Exception {
        String[] expressionArray = getFilteredArrayFromInput(mathExpression);
        String isMathRegularExpression = "^[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)\\s[\\+\\-\\/\\*]\\s[\\-]?([I|X|V|C|L|D|M|i|x|v|c|l|d|m]+|[\\d]+)$";
        String isNegativeNumberRegularExpression = "^[\\-]([I|X|V|C|L|D|M]+|[\\d]+)$";
        if (!mathExpression.matches(isMathRegularExpression))
            throw new Exception("Некорректный формат математической операции");
        setExpressionType(mathExpression);
        if (!operators.contains(expressionArray[1])) throw new Exception("Некоректное математическое действие");
        if (expressionArray[0].matches(isNegativeNumberRegularExpression) || expressionArray[2].matches(isNegativeNumberRegularExpression)) {
            if (Objects.equals(expressionType, romeType))
                throw new Exception("В римской системе счисления нет отрицательных чисел");
            throw new Exception("Ошибка : отрицательные числа запрещены");
        }
    }


    private void setExpressionType(String mathExpression) throws Exception {
        boolean isRomeRegularExpression = mathExpression.matches("^[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+\\s[\\+\\-\\/\\*]\\s[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+$");
        boolean isArabicExpression = mathExpression.matches("^[\\-]?[\\d]+\\s[\\+\\-\\/\\*]\\s[\\-]?[\\d]+$");
        if (isRomeRegularExpression && isArabicExpression) throw new Exception("Используются разные системы счисления");
        if (isArabicExpression) {
            expressionType = arabicType;
        } else if (isRomeRegularExpression) {
            expressionType = romeType;
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
        String[] expressionArray = getFilteredArrayFromInput(mathExpression);
        String smallRomeNumbers = "[i|x|v|c|l|d|m]";
        if (Objects.equals(expressionType, romeType) && (expressionArray[0].matches(smallRomeNumbers) || expressionArray[2].matches(smallRomeNumbers)))
            throw new Exception("В римской системе буквы должны быть заглавными");

        if (Objects.equals(expressionType, romeType)) {
            checkRomeNumbers(expressionArray[0], expressionArray[2]);
            x = convertRomeToArabNumber(expressionArray[0]);
            y = convertRomeToArabNumber(expressionArray[2]);
        } else {
            x = Integer.parseInt(expressionArray[0]);
            y = Integer.parseInt(expressionArray[2]);
        }
        if ((x < 0 || x > 10) || (y < 0 || y > 10))
            throw new Exception("Числа должны быть в промежутке от 0 до 10");
        if (x == 0 && y == 0 && Objects.equals(action, "/")) throw new Exception("Нельзя делить на 0");
    }

    private void checkRomeNumbers(String number1, String number2) throws Exception {
        // Если у нас подряд идёт > 4 I (любое число), то уже некоректно введено
        // Непонятно как поступать с, к примеру, IVI = 5 - по сути введено правильно, но число 5 имеет своё обозначение V
        if (number1.matches("^IIII$")) throw new Exception("Некоректно введено число : " + number1);
        if (number2.matches("^IIII$")) throw new Exception("Некоректно введено число : " + number2);
    }

    private Integer convertRomeToArabNumber(String romeNumber) throws Exception {
        int romeNumberLength = romeNumber.length() - 1;
        char[] charRomeNumber = romeNumber.toCharArray();
        int summ = 0;
        for (int i = romeNumberLength; i >= 0; i--) {
            int currentNumber = romeNumbers.get(charRomeNumber[i] + "");
            if (i - 1 >= 0) {
                int prevNumber = romeNumbers.get(charRomeNumber[i - 1] + "");
                if (currentNumber > prevNumber) {
                    summ += romeNumbers.get(charRomeNumber[i - 1] + "" + charRomeNumber[i]);
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
        return "I".repeat((int) number).replace("IIIII", "V").replace("IIII", "IV").replace("VV", "X").replace("VIV", "IX").replace("XXXXX", "L").replace("XXXX", "XL").replace("LL", "C").replace("LXL", "XC").replace("CCCCC", "D").replace("CCCC", "CD").replace("DD", "M").replace("DCD", "CM");
    }


    private String[] getFilteredArrayFromInput(String str) {
        return Arrays.stream(str.split(" ")).filter((x) -> !x.isEmpty()).toArray(String[]::new);
    }

    private static Map<String, Integer> createRomeNumbersMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("II", 2);
        map.put("III", 3);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("VI", 6);
        map.put("VII", 7);
        map.put("IIX", 8);
        map.put("IX", 9);
        map.put("X", 10);
        return map;
    }

}
