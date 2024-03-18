package org.example.Numbers;

import java.util.List;

public interface DifferentNumberInterface {
    List<Integer> getNumbers();
    void checkResult(float resultNumber) throws Exception;
    String convertResult(float number, String action);

}
