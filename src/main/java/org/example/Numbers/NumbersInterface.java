package org.example.Numbers;

import java.util.List;

public interface NumbersInterface {
    List<Integer> getNumbers();
    String convertResult(float resultNumber,String action) throws Exception;
}
