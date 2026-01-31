package org.example.presentation;

import java.util.Scanner;
import org.example.application.InputType;
import org.example.application.sorting.SortAlgorithm;
import org.example.application.sorting.SortCondition;
import org.example.application.sorting.SortType;
import org.example.domain.Bus;
import org.example.exception.BusServiceException;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.SingletonScanner;

/**
 * Abstract controller class for the application.
 * @param <T> the type of elements being processed
 */

public class ApplicationController {

    private final Scanner sc = SingletonScanner.getScanner();
    
    public InputType askInputType() {
        System.out.println("Выберите способ заполнения коллекции.");
        int fillMode = askInteger("1. Из файла\n2. Случайно\n3. Вручную\nСпособ → \n");
        return switch(fillMode) {
            case 1 -> InputType.INPUT_FROM_FILE;
            case 2 -> InputType.INPUT_RANDOM;
            case 3 -> InputType.INPUT_MANUAL;
            default -> throw new BusServiceException("Wrong input type number!");
        };
    }

    public SortType askSortType() {
        System.out.println("Выберите способ сортировки коллекции.");
        int sortMode = askInteger("1. По всем полям.\n2. По номеру.\n3. По модели.\\n" + //
                        "4. По пробегу.\nСпособ → \n");
        return switch (sortMode) {
            case 1 -> SortType.SORT_BASIC;
            case 2 -> SortType.SORT_NUMBER;
            case 3 -> SortType.SORT_MODEL;
            case 4 -> SortType.SORT_MILEAGE;
            default -> throw new BusServiceException("Wrong sort type number!");
        };
    }

    public String askString(String message) {
        System.out.println(message);
        while (true) {
            return sc.nextLine().trim();
        }
    }

    public Integer askInteger(String message) {
        System.out.println(message);
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число!\n");
            }
        }
    }

    public SortAlgorithm askSortAlgorithm() {
        System.out.println("Выберите алгоритм сортировки коллекции.");
        int sortMode = askInteger("1. Сортировка пузырьком.\n2. Быстрая сортировка.\n3. Выборочная сортировка.\n" + //
                "Способ → \n");
        return switch (sortMode) {
            case 1 -> SortAlgorithm.SORT_BUBBLE;
            case 2 -> SortAlgorithm.SORT_QUICKSORT;
            case 3 -> SortAlgorithm.SORT_SELECTION;
            default -> throw new BusServiceException("Wrong sort algorithm number!");
        };
    }

    public SortCondition askSortCondition() {
        System.out.println("Выберите условие сортировки коллекции.");
        int sortMode = askInteger("1. Сортировка чётных индексов.\n2. Сортировка нечётных индексов.\n3. Безусловная сортировка.\n" + //
                "Способ → \n");
        return switch (sortMode) {
            case 1 -> SortCondition.SORT_EVEN_ONLY;
            case 2 -> SortCondition.SORT_ODD_ONLY;
            case 3 -> SortCondition.SORT_ALL;
            default -> throw new BusServiceException("Wrong sort condition number!");
        };
    }

    public Boolean askBoolean(String message) {
        System.out.println(message);
        int sortMode = askInteger("1. Да.\n2. Нет.\nСпособ → \n");
        return switch (sortMode) {
            case 1 -> true;
            case 2 -> false;
            default -> throw new BusServiceException("No such option!");
        };
    }

    public void showList(CustomList<Bus> buses) {
        System.out.println(buses.toString());
    }
}
