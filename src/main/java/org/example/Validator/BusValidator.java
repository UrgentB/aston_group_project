package org.example.Validator;

import org.example.domain.Bus;

public class BusValidator {
    //Простая валидация передаваемой строки
    public Bus parcer(String line){
        Bus bus = null;
        if(line == null || line.trim().isEmpty()){
            System.out.println("Пустая строка");
        }

        String[] parts = line.split(",");
        if(parts.length != 3){
            System.out.println("Неверное количество полей: " + parts.length);
        }

        try {
            Integer number = Integer.parseInt(parts[0].trim());
            String model = parts[1].trim();
            double mileage = Double.parseDouble(parts[2].trim());
            if(mileage < 0){
                System.out.println("Пробег не может быть отрицательным");
            }
            return new Bus(number, model, mileage);
        }catch (NumberFormatException exception){
            System.out.println("Ошибка в преобразовании числа" + "\n" + exception);
            throw new NumberFormatException();
        }
    }
}
