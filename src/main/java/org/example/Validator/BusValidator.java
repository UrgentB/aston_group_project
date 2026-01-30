package org.example.Validator;

import org.example.domain.Bus;
import org.example.fill.CheckHelp;

public class BusValidator {
    //Простая валидация передаваемой строки
    public Bus parcer(String line){
        if(line == null || line.trim().isEmpty()){
            System.out.println("Пустая строка");
            return null;
        }

        String[] parts = line.split(",");
        if(parts.length != 3){
            System.out.println("Неверное количество полей: " + parts.length);
            return null;
        }

        try {
            Integer number = Integer.parseInt(parts[0].trim());
            String model = parts[1].trim();
            double mileage = Double.parseDouble(parts[2].trim());
            if(CheckHelp.busCheck(number, model, mileage)){
                return new Bus.Builder()
                        .number(number)
                        .model(model)
                        .mileage(mileage)
                        .build();
            }

        }catch (NumberFormatException exception){
            System.out.println("Ошибка в преобразовании числа" + "\n" + exception);
        }

        return null;
    }
}
