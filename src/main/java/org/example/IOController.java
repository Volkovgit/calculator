package org.example;

import java.io.IOException;
import java.util.Scanner;

public class IOController {

    public String readLine() throws IOException{
        try(Scanner scanner = new Scanner(System.in)){
            return scanner.nextLine();
        }
    }

    public void printLine(String str) {
        System.out.println(str);
    }
}
