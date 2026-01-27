package org.example.multithreading;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Многопоточный счетчик вхождений элемента в коллекцию
 * Дополнительное задание №4
 */
public class MultithreadedCounter<T> {
    private final List<T> collection;
    private final int threadCount;
    
    public MultithreadedCounter(List<T> collection, int threadCount) {
        if (collection == null) {
            throw new IllegalArgumentException("Коллекция не может быть null");
        }
        this.collection = collection;
        this.threadCount = Math.max(1, threadCount);
    }
    
    /**
     * Подсчитывает количество вхождений элемента в коллекцию
     * @param element искомый элемент
     * @return количество вхождений
     */
    public int countOccurrences(T element) {
        if (collection.isEmpty()) {
            return 0;
        }
        
        int size = collection.size();
        int chunkSize = (size + threadCount - 1) / threadCount; // округление вверх
        
        // Создаем пул потоков
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        AtomicInteger totalCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        try {
            // Разделяем работу между потоками
            for (int i = 0; i < threadCount; i++) {
                final int start = i * chunkSize;
                final int end = Math.min(start + chunkSize, size);
                
                executor.submit(() -> {
                    try {
                        int localCount = 0;
                        // Каждый поток обрабатывает свою часть коллекции
                        for (int j = start; j < end; j++) {
                            T current = collection.get(j);
                            if (current != null && current.equals(element)) {
                                localCount++;
                            }
                        }
                        // Атомарно добавляем к общему счетчику
                        totalCount.addAndGet(localCount);
                    } finally {
                        latch.countDown(); // Сигнализируем о завершении
                    }
                });
            }
            
            // Ждем завершения всех потоков
            latch.await();
            return totalCount.get();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Подсчет был прерван: " + e.getMessage());
            return 0;
        } finally {
            // Завершаем работу пула потоков
            executor.shutdown();
        }
    }
    
    /**
     * Подсчитывает и выводит результат в консоль
     */
    public void countAndPrint(T element) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("МНОГОПОТОЧНЫЙ ПОДСЧЕТ ВХОЖДЕНИЙ");
        System.out.println("=".repeat(60));
        
        System.out.println("Размер коллекции: " + collection.size());
        System.out.println("Используется потоков: " + threadCount);
        System.out.println("Искомый элемент: " + element);
        
        long startTime = System.nanoTime();
        int count = countOccurrences(element);
        long endTime = System.nanoTime();
        
        double elapsedMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("\nРЕЗУЛЬТАТЫ:");
        System.out.println("-".repeat(40));
        System.out.println("Найдено вхождений: " + count);
        System.out.printf("Время выполнения: %.3f мс\n", elapsedMs);
        System.out.println("=".repeat(60) + "\n");
    }
    
    /**
     * Возвращает рекомендуемое количество потоков для текущей системы
     */
    public static int getRecommendedThreadCount() {
        int processors = Runtime.getRuntime().availableProcessors();
        return Math.max(2, processors);
    }
}
