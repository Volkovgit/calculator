package org.example.Calculator;

public class Calculator implements CalculatorInterface {

    public int sum(int x, int y) {
        return x + y;
    }

    public int sub(int x, int y) {
        return x - y;
    }

    public int mult(int x, int y) {
        return x * y;
    }

    public float div(int x, int y) {
        return (float) x / y;
    }

}