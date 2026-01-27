package org.example;

import org.example.application.comparators.BasicBusComparator;
import org.example.application.comparators.MileageBusComparator;
import org.example.application.comparators.ModelBusComparator;
import org.example.application.comparators.NumberBusComparator;
import org.example.domain.Bus;
import org.example.domain.SortType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BusSortingApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        List<Bus> currentBuses = null;
        while (true) {
            System.out.println("\n1. Запустить сортировку");
            System.out.println("2. Многопоточный подсчет вхождений");
            System.out.println("3. Выход");
            System.out.print("→ ");

            int choice = readInt(1, 3);
            if (choice == 3) {
                System.out.println("До свидания!");
                break;
            }
            if (choice == 2) {
                if (currentBuses == null || currentBuses.isEmpty()) {
                    System.out.println("Сначала запустите сортировку");
                    continue;
                }
                runMultithreadedCounter(currentBuses);
                continue;
            }

            System.out.print("Количество автобусов: ");
            int size = readInt(1, 10000);

            System.out.println("1. Из файла    2. Случайно    3. Вручную");
            System.out.print("Способ → ");
            int fillMode = readInt(1, 3);

            List<Bus> buses = new ArrayList<>();

            for (int i = 1; i <= size; i++) {
                buses.add(new Bus.Builder()
                        .number(i)
                        .model("Model-" + i)
                        .mileage(1000.0 * i)
                        .build());
            }

            if (buses.isEmpty()) {
                System.out.println("Список пустой\n");
                continue;
            }

            currentBuses = buses;

            System.out.println("\nСортировать по:");
            System.out.println("1. " + SortType.SORT_NUMBER.getTitle());
            System.out.println("2. " + SortType.SORT_MODEL.getTitle());
            System.out.println("3. " + SortType.SORT_MILEAGE.getTitle());
            System.out.println("4. " + SortType.SORT_BASIC.getTitle());
            System.out.print("→ ");

            int sortChoice = readInt(1, 4);
            SortType sortType = switch (sortChoice) {
                case 1 -> SortType.SORT_NUMBER;
                case 2 -> SortType.SORT_MODEL;
                case 3 -> SortType.SORT_MILEAGE;
                case 4 -> SortType.SORT_BASIC;
                default -> SortType.SORT_BASIC;
            };

            Comparator<Bus> comparator = switch (sortType) {
                case SORT_NUMBER    -> new NumberBusComparator();
                case SORT_MODEL     -> new ModelBusComparator();
                case SORT_MILEAGE   -> new MileageBusComparator();
                case SORT_BASIC     -> new BasicBusComparator();
            };

            buses.sort(comparator);

            System.out.println("\nОтсортированный список:");
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
        /Многопоточный подсчет вхождений автобуса в коллекцию
     
    private static void runMultithreadedCounter(List<Bus> buses) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("МНОГОПОТОЧНЫЙ ПОДСЧЕТ ВХОЖДЕНИЙ");
        System.out.println("=".repeat(50));
        
        System.out.println("Размер коллекции: " + buses.size());
        
        // Первый автобус как пример для поиска
        Bus target = buses.get(0);
        System.out.println("Ищем автобус: " + target);
        System.out.println("(используется первый автобус из коллекции)");
        
        System.out.print("\nКоличество потоков (1-8): ");
        int threads = readInt(1, 8);
        
        long startTime = System.nanoTime();
        
        // многопоточный счетчик
        int chunkSize = buses.size() / threads;
        // thread-safe подсчет
        java.util.concurrent.atomic.AtomicInteger count = new java.util.concurrent.atomic.AtomicInteger(0);
        // ожидание завершения всех потоков
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(threads);
        
        System.out.println("\nЗапуск " + threads + " потоков...");
        
        for (int i = 0; i < threads; i++) {
            final int threadId = i;
            final int start = i * chunkSize;
            final int end = (i == threads - 1) ? buses.size() : start + chunkSize;
            
            // Создаем и запускаем поток
            new Thread(() -> {
                try {
                    int localCount = 0;
                    // Каждый поток обрабатывает свою часть коллекции
                    for (int j = start; j < end; j++) {
                        if (buses.get(j).equals(target)) {
                            localCount++;
                        }
                    }
                    // Атомарно добавляем к общему счетчику
                    count.addAndGet(localCount);
                    System.out.println("  Поток " + threadId + " завершил работу. Найдено: " + localCount);
                } finally {
                    // Сигнализируем о завершении
                    latch.countDown();
                }
            }).start();
        }
        
        try {
            // Ждем завершения всех потоков
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Подсчет был прерван!");
        }
        
        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("РЕЗУЛЬТАТЫ:");
        System.out.println("-".repeat(30));
        System.out.println("Найдено вхождений: " + count.get());
        System.out.printf("Время выполнения: %.3f мс\n", timeMs);
        System.out.println("Использовано потоков: " + threads);
        System.out.println("=".repeat(50));
    }
}
}
