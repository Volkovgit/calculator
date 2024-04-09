package org.example.Numbers;

import java.util.List;

public interface DifferentNumber {
    List<Integer> getNumbers();
    void checkResult(float resultNumber) throws Exception;
    String convertResult(float number, String action);

}
