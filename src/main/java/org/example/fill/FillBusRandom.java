package org.example.fill;

import org.example.domain.Bus;

import java.util.Scanner;
import java.util.Random;

public class FillBusRandom implements FillBus {

    private final String MES_COUNT = "Ведите число автобусов: ";
    private final String[] MODELS = {"test1","test2","test3","test4","test5","test6","test7","test8","test9","test10"};
    private static final Random RANDOM = new Random();
    private static final Integer MIN_NUMBER = 1;
    private static final Integer MAX_NUMBER = 100;
    private static final Double MIN_MILEAGE = 0.0;
    private static final Double MAX_MILEAGE = 10000.0;


    @Override
    public Bus[] fill() {
        Scanner in = new Scanner(System.in);

        Integer countBus = InputHelp.getIntField(MES_COUNT, in);

        Bus[] buses = null;

        if(countBus != null && countBus > 0) {
            buses = new Bus[countBus];
        } else countBus = 0;


        for(int i = 0; i < countBus; i++) {
            Integer number = MIN_NUMBER + RANDOM.nextInt(MAX_NUMBER - MIN_NUMBER + 1);

            String model = MODELS[RANDOM.nextInt(MODELS.length)];

            Double mileage = MIN_MILEAGE + RANDOM.nextDouble() * (MAX_MILEAGE - MIN_MILEAGE);

            buses[i] = Bus.createInstance(number, model, mileage);

        }

        Bus[] buses_result = null;

        if(buses != null) {
            buses_result = new Bus[buses.length];
            System.arraycopy(buses, 0, buses_result, 0, buses.length);
        }

        return buses_result;
    }

}
