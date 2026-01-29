package org.example.fill;

public class CheckHelp {
    public static Boolean busCheck(Integer number, String model, Double mileage) {
        boolean result = true;

        if (number == null) {
            System.out.println("Номер автобуса должен быть числовом!");
            result = false;
        } else {
            if (number < 0) {
                System.out.println("Номер автобуса не может быть отрицательным!");
                result = false;
            }
        }

        if (model.trim().isEmpty()) {
            System.out.println("Модель не можит быть пустой");
            result = false;
        }

        if (mileage == null) {
            System.out.println("Пробег автобуса должен быть числовом!");
            result = false;
        } else {
            if (mileage < 0) {
                System.out.println("Пробег автобуса не может быть отрицательным!");
                result = false;
            }
        }
        return result;
    }

}
