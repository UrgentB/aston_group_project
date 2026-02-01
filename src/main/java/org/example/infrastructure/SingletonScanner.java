package org.example.infrastructure;

import java.io.InputStream;
import java.util.Scanner;

public class SingletonScanner {
    private Scanner scanner;

    private static SingletonScanner instance = new SingletonScanner(System.in);

    private SingletonScanner(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static SingletonScanner getInstance() {
        if (instance == null) {
            instance = new SingletonScanner(System.in);
        }
        return instance;
    }

    public static void reset(InputStream inputStream) {
        instance = new SingletonScanner(inputStream);
    }
}
