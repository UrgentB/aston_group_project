package org.example.fill;

import org.example.Bus;

import java.util.Scanner;

public class FillBusManually implements FillBus {

    private final String MES_COUNT = "Ведите число автобусов: ";
    private final String MES_AUTHOR = "Введите номер автобуса: ";
    private final String MES_TITLE = "Введите модель автобуса: ";
    private final String MES_PAGES = "Введите пробег автобуса: ";
    private final String MES_ERROR_BUILD = "Невозможно создать автобус с такими параметрами";

    public Bus[] fill() {
        Scanner in = new Scanner(System.in);

        Integer countBus = InputHelp.getIntField(MES_COUNT, in);

        Bus[] buses = null;

        if(countBus != null && countBus > 0) {
            buses = new Bus[countBus];
        } else countBus = 0;

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

        Bus[] buses_result = null;
        if(j > 0) {
            buses_result = new Bus[j];
            System.arraycopy(buses, 0, buses_result, 0, j);
        }

        return buses_result;
    }
}
