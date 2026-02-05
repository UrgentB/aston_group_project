package org.example;

import org.example.domain.Bus;
import org.example.multithreading.MultithreadedCounter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Демонстрация работы многопоточного счетчика
 */
public class MultithreadingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ МНОГОПОТОЧНОГО ПОДСЧЕТА ===");
        
        // Создаем тестовую коллекцию
        List<Bus> buses = generateTestBuses(10000);
        
        // Целевой автобус для поиска
        Bus targetBus = buses.get(500); // Берем автобус из середины
        
        System.out.println("Размер коллекции: " + buses.size());
        System.out.println("Целевой автобус: " + targetBus);
        System.out.println("Рекомендуемое количество потоков: " + 
                          MultithreadedCounter.getRecommendedThreadCount());
        
        // Создаем счетчик
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(
            buses, 
            MultithreadedCounter.getRecommendedThreadCount()
        );
        
        // Запускаем бенчмарк всех методов
        counter.benchmarkAllMethods(targetBus);
        
        // Дополнительный тест с разным количеством потоков
        testWithDifferentThreadCounts(buses, targetBus);
    }
    
    private static List<Bus> generateTestBuses(int count) {
        List<Bus> buses = new ArrayList<>();
        Random random = new Random();
        String[] models = {"Mercedes", "Volvo", "MAN", "Scania", "Iveco"};
        
        for (int i = 0; i < count; i++) {
            int number = 100 + random.nextInt(900);
            String model = models[random.nextInt(models.length)];
            double mileage = 1000 + random.nextDouble() * 99000;
            
            buses.add(Bus.createInstance(number, model, mileage));
        }
        
        // Добавляем дубликаты для поиска
        for (int i = 0; i < 50; i++) {
            buses.add(buses.get(random.nextInt(buses.size())));
        }
        
        return buses;
    }
    
    private static void testWithDifferentThreadCounts(List<Bus> buses, Bus target) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ТЕСТ С РАЗНЫМ КОЛИЧЕСТВОМ ПОТОКОВ");
        System.out.println("=".repeat(70));
        
        int[] threadCounts = {1, 2, 4, 8, 16};
        
        for (int threads : threadCounts) {
            System.out.println("\nКоличество потоков: " + threads);
            MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, threads);
            
            long startTime = System.nanoTime();
            int count = counter.countOccurrences(target);
            long endTime = System.nanoTime();
            
            double elapsedMs = (endTime - startTime) / 1_000_000.0;
            System.out.printf("  Найдено: %d, Время: %.3f мс\n", count, elapsedMs);
        }
    }
}
