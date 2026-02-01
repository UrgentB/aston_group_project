package org.example;
import org.example.domain.Bus;
import org.example.infrastructure.SingletonScanner;

import java.util.List;
import java.util.Scanner;

public class BusSortingApp {

    private static final Scanner sc = SingletonScanner.getInstance().getScanner();

    public static void main(String[] args) {
        new App().run();
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
        //Многопоточный подсчет вхождений автобуса в коллекцию
     
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
