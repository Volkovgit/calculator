package org.example;
public class Main {
    public static void main(String[] args) throws Exception {
        IOController controller = new IOController();
        controller.printLine("Введи выражение");
        String strInput = controller.readLine();
        InputHandler mathExpr = new InputHandler();
        controller.printLine(mathExpr.getResult(strInput));
    }
}