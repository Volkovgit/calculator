package org.example.Numbers;


import java.util.Map;
import java.util.List;

public class RomeNumbers implements DifferentNumberInterface{

    private final String NOT_ZERO_IN_ROME = "В римской системе счисления нет 0";
    private final String NEGATIVE_NUMBER_IN_ROME = "В римской системе счисления нет отрицательных чисел";
    private final String smallRomeNumbers = "[i|x|v|c|l|d|m]";
    private final String isNegativeRomeNumberRegularExpression = "^[\\-]([I|X|V|C|L|D|M]+)$";
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

    private int firstNumber;
    private int secondNumber;

    public RomeNumbers(String firstNumberString, String secondNumberString) throws Exception {
        checkRomeNumbers(firstNumberString, secondNumberString);
        firstNumber = convertRomeToArabNumber(firstNumberString);
        secondNumber = convertRomeToArabNumber(secondNumberString);
    }

    public List<Integer> getNumbers() {
        return List.of(firstNumber, secondNumber);
    }

    public void checkResult(float result) throws Exception {
        if (result == 0) {
            throw new Exception(NOT_ZERO_IN_ROME);
        }
        if (result < 0) {
            throw new Exception(NEGATIVE_NUMBER_IN_ROME);
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
        if (number1.matches(isNegativeRomeNumberRegularExpression) || number2.matches(isNegativeRomeNumberRegularExpression)) {
            throw new Exception("В римской системе счисления нет отрицательных чисел");
        }
        if (number1.matches(smallRomeNumbers) || number2.matches(smallRomeNumbers)) {
            throw new Exception("В римской системе буквы должны быть заглавными");
        }
        if (number1.matches("^IIII$")) {
            throw new Exception("Некоректно введено число : " + number1);
        }
        if (number2.matches("^IIII$")) {
            throw new Exception("Некоректно введено число : " + number2);
        }
    }

    public String convertResult(float number,String action) {
        if (number % 10 != 0) {
            number = (float) Math.floor(number);
        }
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


}
