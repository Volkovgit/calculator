package org.example.Numbers;

import java.util.List;

public class ArabNumbers {

    private int firstNumber;
    private int secondNumber;

    public ArabNumbers(String firstNumberString, String secondNumberString) throws Exception {
        firstNumber = Integer.parseInt(firstNumberString);
        secondNumber = Integer.parseInt(secondNumberString);
    }

    public List<Integer> getNumbers() {
        return List.of(firstNumber, secondNumber);
    }

}
