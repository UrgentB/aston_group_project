package org.example.infrastructure;

import java.util.Scanner;

public class SingletonScanner {
    private static Scanner scanner;

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}
