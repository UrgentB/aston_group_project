package org.example.fill;

import java.util.Scanner;

public class InputHelp {
    public static Integer getIntField(String message, Scanner in) {
        Integer temp = null;
        System.out.println(message);
        if (in.hasNextInt()) {
            temp = in.nextInt();
        }
        in.nextLine();
        return  temp;
    }

    public static Double getDoubleField(String message, Scanner in) {
        Double temp = null;
        System.out.println(message);
        if (in.hasNextDouble()) {
            temp = in.nextDouble();
        }
        in.nextLine();
        return temp;
    }

    public static String getStringField(String message, Scanner in) {
        System.out.println(message);
        return in.nextLine();
    }
}
