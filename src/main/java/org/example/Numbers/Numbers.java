package org.example.Numbers;

import java.util.List;
import java.util.Objects;

public class Numbers implements NumbersInterface {
    private RomeNumbers romeNumbers;
    private ArabNumbers arabNumbers;
    private String numbersType;
    private final String ROME_TYPE = "ROME";
    private final String ARABIC_TYPE = "ARAB";
    private final String isRomeRegularExpression = "^[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+\\s[\\+\\-\\/\\*]\\s[\\-]?[I|X|V|C|L|D|M|i|x|v|c|l|d|m]+$";
    private final String isArabicRegularExpression = "^[\\-]?[\\d]+\\s[\\+\\-\\/\\*]\\s[\\-]?[\\d]+$";
    private final String DIFFERENT_EXPRESSION_TYPE = "Используются разные системы счисления";


    public Numbers(List<String> mathExpressionLikeArray) throws Exception {
        setNumbersType(String.join(" ", mathExpressionLikeArray));
        if (Objects.equals(ROME_TYPE, numbersType)) {
            romeNumbers = new RomeNumbers(mathExpressionLikeArray.get(0), mathExpressionLikeArray.get(2));
            numbersType = ROME_TYPE;
        } else {
            arabNumbers = new ArabNumbers(mathExpressionLikeArray.get(0), mathExpressionLikeArray.get(2));
            numbersType = ARABIC_TYPE;
        }
        checkNumbersRange();
    }

    private void setNumbersType(String mathExpression) throws Exception {
        boolean isRomeExpression = mathExpression.matches(isRomeRegularExpression);
        boolean isArabicExpression = mathExpression.matches(isArabicRegularExpression);
        if (!isRomeExpression && !isArabicExpression) {
            throw new Exception(DIFFERENT_EXPRESSION_TYPE);
        }
        if (isArabicExpression) {
            numbersType = ARABIC_TYPE;
        } else {
            numbersType = ROME_TYPE;
        }
    }

    private void checkNumbersRange() throws Exception {
        List<Integer> numbers = getNumbers();
        if ((numbers.get(0) < 0 || numbers.get(0) > 10) || (numbers.get(1) < 0 || numbers.get(1) > 10))
            throw new Exception("Числа должны быть в промежутке от 0 до 10");

    }

    public List<Integer> getNumbers() {
        if (Objects.equals(ROME_TYPE, numbersType)) {
            return romeNumbers.getNumbers();
        } else {
            return arabNumbers.getNumbers();
        }
    }

    public String convertResult(float resultNumber,String action) throws Exception {
        if (Objects.equals(ROME_TYPE, numbersType)) {
            romeNumbers.checkResult(resultNumber);
            return romeNumbers.convertResult(resultNumber,action);
        } else{
            return arabNumbers.convertResult(resultNumber,action);
        }
    }

}
