package org.example;

import java.util.Scanner;

public class IOController {

    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        scanner.close();
        return inputLine;
    }

    public void printLine(String str) {
        System.out.println(str);
    }
}
