package org.example.infrastructure;

import java.io.InputStream;
import java.util.Scanner;

public class SingletonScanner {
    private Scanner scanner;

    private static SingletonScanner singletonScanner = new SingletonScanner(System.in);

    private SingletonScanner(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static SingletonScanner getInstance() {
        return singletonScanner;
    }
}
