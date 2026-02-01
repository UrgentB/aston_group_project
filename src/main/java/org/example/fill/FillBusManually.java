package org.example.fill;

import org.example.domain.Bus;
import org.example.exception.InvalidUserInputException;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.SingletonScanner;

import java.util.Scanner;

public class FillBusManually implements FillBus {

    private final String MES_COUNT = "Ведите число автобусов: ";
    private final String MES_AUTHOR = "Введите номер автобуса: ";
    private final String MES_TITLE = "Введите модель автобуса: ";
    private final String MES_PAGES = "Введите пробег автобуса: ";
    private final String MES_ERROR_BUILD = "Невозможно создать автобус с такими параметрами";

    public CustomList<Bus> fill() {
        Scanner in = SingletonScanner.getInstance().getScanner();

        Integer countBus = InputHelp.getIntField(MES_COUNT, in);

        if (countBus == null) {
            throw new InvalidUserInputException("Invalid bus count!");
        }

        CustomList<Bus> buses = new CustomList<Bus>();

        for (int i = 0; i < countBus; i++) {
            Integer number = InputHelp.getIntField(MES_AUTHOR, in);

            String model = InputHelp.getStringField(MES_TITLE, in);

            Double mileage = InputHelp.getDoubleField(MES_PAGES, in);

            if (CheckHelp.busCheck(number, model, mileage)) {
                buses.add(Bus.createInstance(number, model, mileage));
            } else {
                System.out.println(MES_ERROR_BUILD);
            }
        }

        return buses;
    }
}