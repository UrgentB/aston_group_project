package org.example.fill;

import org.example.domain.Bus;
import org.example.exception.InvalidUserInputException;
import org.example.infrastructure.CustomList;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class StreamFillBusRandom implements FillBus {

    private final String MES_COUNT = "Ведите число автобусов: ";
    private final String[] MODELS = {"ПАЗ-3205", "ПАЗ-3204", "ПАЗ-4234", "НЕФАЗ-5299", "ЛИАЗ-5292", "ЛИАЗ-6213", "МАЗ-206", "МАЗ-103", "Iveco SFR 160", "КАВЗ-4238"};
    private static final Random RANDOM = new Random();
    private static final Integer MIN_NUMBER = 1;
    private static final Integer MAX_NUMBER = 100;
    private static final Double MIN_MILEAGE = 0.0;
    private static final Double MAX_MILEAGE = 10000.0;


    @Override
    public CustomList<Bus> fill() {
        Scanner in = new Scanner(System.in);

        Integer countBus = InputHelp.getIntField(MES_COUNT, in);

        if (countBus == null || countBus < 0) {
            throw new InvalidUserInputException("Invalid bus count!");
        }

        Bus[] buses = new Bus[countBus];

        for(int i = 0; i < countBus; i++) {
            Integer number = MIN_NUMBER + RANDOM.nextInt(MAX_NUMBER - MIN_NUMBER + 1);

            String model = MODELS[RANDOM.nextInt(MODELS.length)];

            Double mileage = MIN_MILEAGE + RANDOM.nextDouble() * (MAX_MILEAGE - MIN_MILEAGE);

            buses[i] = Bus.createInstance(number, model, mileage);

        }

        CustomList<Bus> buses_result = null;

        if(buses != null) {
            buses_result = new CustomList<>();
            Arrays.stream(buses).forEach(buses_result::add);
        }

        return buses_result;
    }

}
