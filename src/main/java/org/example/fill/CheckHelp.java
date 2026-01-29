package org.example.fill;

public class CheckHelp {
    public static Boolean busCheck(Integer number, String model, Double mileage) {
        return number > 0 && !model.isEmpty() && mileage > 0;
    }

}
