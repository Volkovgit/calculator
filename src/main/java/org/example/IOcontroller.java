package org.example;

import java.util.Scanner;

public class IOcontroller {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }

    public void printLine(String str) {
        System.out.println(str);
    }
}
