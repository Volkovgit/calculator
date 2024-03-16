package org.example;

import org.example.InputHandler.InputHandler;

public class CalculationService {
    CalculationService() {}

    public void startCalculationProcess() throws Exception {
        IOController controller = new IOController();
        controller.printLine("Введи выражение");
        String strInput = controller.readLine();
        InputHandler mathExpr = new InputHandler();
        controller.printLine(mathExpr.getResult(strInput));
    }
}
