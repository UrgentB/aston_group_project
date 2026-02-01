package org.example.fill;

import org.example.domain.Bus;
import org.example.exception.InvalidUserInputException;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.SingletonScanner;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class StreamFillBusManually implements FillBus {

    private final String MES_COUNT = "Ведите число автобусов: ";
    private final String MES_AUTHOR = "Введите номер автобуса: ";
    private final String MES_TITLE = "Введите модель автобуса: ";
    private final String MES_PAGES = "Введите пробег автобуса: ";
    private final String MES_ERROR_BUILD = "Невозможно создать автобус с такими параметрами";

    public CustomList<Bus> fill() {
        Scanner in = SingletonScanner.getInstance().getScanner();

        Integer countBus = InputHelp.getIntField(MES_COUNT, in);

        if (countBus == null || countBus < 0) {
            throw new InvalidUserInputException("Invalid bus count!");
        }

        Bus[] buses = new Bus[countBus];

        int j = 0;

        for(int i = 0; i < countBus; i++) {
            Integer number = InputHelp.getIntField(MES_AUTHOR, in);

            String model = InputHelp.getStringField(MES_TITLE, in);

            Double mileage = InputHelp.getDoubleField(MES_PAGES, in);

            if(CheckHelp.busCheck(number, model, mileage)) {
                buses[j] = Bus.createInstance(number, model, mileage);
                j++;
            } else {
                System.out.println(MES_ERROR_BUILD);
            }
        }

        CustomList<Bus> buses_result = null;
        if(j > 0) {
            buses_result = new CustomList<>();
            Arrays.stream(buses).filter(Objects::nonNull).forEach(buses_result::add);
        }

        return buses_result;
    }
}
