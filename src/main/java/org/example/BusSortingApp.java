package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BusSortingApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Запустить сортировку");
            System.out.println("2. Выход");
            System.out.print("→ ");

            int choice = readInt(1, 2);
            if (choice == 2) {
                System.out.println("До свидания!");
                break;
            }

            System.out.print("Количество автобусов: ");
            int size = readInt(1, 10000);

            System.out.println("1. Из файла    2. Случайно    3. Вручную");
            System.out.print("Способ → ");
            int fillMode = readInt(1, 3);

            List<Bus> buses = new ArrayList<>();

            // Имитация заполнения (пока просто id от 1 до size)
            for (int i = 1; i <= size; i++) {
                buses.add(new Bus.Builder().id(i).build());
            }

            if (buses.isEmpty()) {
                System.out.println("Список пустой\n");
                continue;
            }

            System.out.println("\nСортировать по:");
            System.out.println("1. Номеру (по умолчанию)");
            System.out.println("2. Модели (пока заглушка)");
            System.out.println("3. Пробегу (пока заглушка)");
            System.out.print("→ ");
            int sortMode = readInt(1, 3);

            Comparator<Bus> comparator = switch (sortMode) {
                case 1 -> new Bus();  // встроенный compare из класса Bus
                case 2 -> Comparator.comparing(b -> "");   // заглушка
                case 3 -> Comparator.comparingInt(b -> 0); // заглушка
                default -> new Bus();
            };

            buses.sort(comparator);

            System.out.println("\nОтсортированный список (пока по id):");
            buses.forEach(System.out::println);
        }

        sc.close();
    }

    private static int readInt(int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("Введите число от %d до %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число: ");
            }
        }
    }
}