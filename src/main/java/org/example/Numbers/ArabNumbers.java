package org.example.Numbers;

import java.util.List;
import java.util.Objects;

public class ArabNumbers implements DifferentNumberInterface{

    private final String NEGATIVE_NUMBER = "Ошибка : отрицательные числа запрещены";
    private final String isNegativeNumberRegularExpression = "^[\\-]([\\d]+)$";
    private int firstNumber;
    private int secondNumber;

    public ArabNumbers(String firstNumberString, String secondNumberString) throws Exception {
        checkNumbers(firstNumberString,secondNumberString);
        firstNumber = Integer.parseInt(firstNumberString);
        secondNumber = Integer.parseInt(secondNumberString);
    }

    private void checkNumbers(String numberOne, String numberTwo) throws Exception{
        if (numberOne.matches(isNegativeNumberRegularExpression) || numberTwo.matches(isNegativeNumberRegularExpression)) {
            throw new Exception(NEGATIVE_NUMBER);
        }
    }

    public String convertResult(float resultNumberString, String action){
        if (!Objects.equals(action, "/")) {
            return Integer.toString((int) resultNumberString);
        }
        return Float.toString(resultNumberString);
    }

    public List<Integer> getNumbers() {
        return List.of(firstNumber, secondNumber);
    }

    public void checkResult(float number){
        // Если честно, я не придумал что тут проверять, но чтобы поддержать интерфейс пришлось написать этот метод
    }

}
