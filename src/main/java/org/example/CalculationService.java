package org.example;

import org.example.InputHandler.InputHandlerImpl;

public class CalculationService {
    CalculationService() {}

    public void startCalculationProcess() throws Exception {
        IOController controller = new IOController();
        controller.printLine("Введи выражение");
        String strInput = controller.readLine();
        InputHandlerImpl mathExpr = new InputHandlerImpl();
        controller.printLine(mathExpr.getResult(strInput));
    }
}
