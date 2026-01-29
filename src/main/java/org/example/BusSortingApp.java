package org.example;

import org.example.application.comparators.BasicBusComparator;
import org.example.application.comparators.MileageBusComparator;
import org.example.application.comparators.ModelBusComparator;
import org.example.application.comparators.NumberBusComparator;
import org.example.domain.Bus;
import org.example.domain.SortType;
import org.example.multithreading.MultithreadedCounter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BusSortingApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        List<Bus> currentBuses = null;
        
        while (true) {
            System.out.println("\n=== СИСТЕМА СОРТИРОВКИ АВТОБУСОВ ===");
            System.out.println("1. Создать и отсортировать коллекцию");
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
                    System.out.println("Сначала создайте коллекцию!");
                    continue;
                }
                runMultithreadedCounter(currentBuses);
                continue;
            }

            System.out.print("\nКоличество автобусов: ");
            int size = readInt(1, 10000);

            System.out.println("\nСпособ заполнения:");
            System.out.println("1. Из файла");
            System.out.println("2. Случайно");
            System.out.println("3. Вручную");
            System.out.print("→ ");
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
            
            SortType sortType;
switch (sortChoice) {
    case 1:
        sortType = SortType.SORT_NUMBER;
        break;
    case 2:
        sortType = SortType.SORT_MODEL;
        break;
    case 3:
        sortType = SortType.SORT_MILEAGE;
        break;
    case 4:
    default:
        sortType = SortType.SORT_BASIC;
        break;
}

            
Comparator<Bus> comparator;
switch (sortType) {
    case SORT_NUMBER:
        comparator = new NumberBusComparator();
        break;
    case SORT_MODEL:
        comparator = new ModelBusComparator();
        break;
    case SORT_MILEAGE:
        comparator = new MileageBusComparator();
        break;
    case SORT_BASIC:
    default:
        comparator = new BasicBusComparator();
        break;
}

            buses.sort(comparator);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("ОТСОРТИРОВАННЫЙ СПИСОК");
            System.out.println("=".repeat(50));
            System.out.println("Количество: " + buses.size());
            System.out.println("Критерий: " + sortType.getTitle());
            System.out.println("-".repeat(50));
            
            int showLimit = Math.min(buses.size(), 10);
            
            for (int i = 0; i < showLimit; i++) {
                System.out.println((i + 1) + ". " + buses.get(i));
            }
            
            if (buses.size() > 10) {
                System.out.println("... и еще " + (buses.size() - 10) + " автобусов");
            }
            
            System.out.println("=".repeat(50));
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
    
    private static Bus createBusForSearch() {
        System.out.println("\nВведите данные автобуса для поиска:");
        
        while (true) {
            try {
                System.out.print("Номер автобуса: ");
                int number = Integer.parseInt(sc.nextLine().trim());
                
                System.out.print("Модель автобуса: ");
                String model = sc.nextLine().trim();
                
                System.out.print("Пробег: ");
                double mileage = Double.parseDouble(sc.nextLine().trim());
                
                return new Bus.Builder()
                        .number(number)
                        .model(model)
                        .mileage(mileage)
                        .build();
                        
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка формата числа. Попробуйте снова.");
            }
        }
    }

    private static void runMultithreadedCounter(List<Bus> buses) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("МНОГОПОТОЧНЫЙ ПОДСЧЕТ ВХОЖДЕНИЙ");
        System.out.println("=".repeat(60));
        
        System.out.println("Размер коллекции: " + buses.size());
        
        System.out.println("\nВыберите автобус для поиска:");
        System.out.println("1. Использовать первый автобус из коллекции");
        System.out.println("2. Ввести автобус вручную");
        System.out.print("→ ");
        
        int targetChoice = readInt(1, 2);
        Bus target;
        
        if (targetChoice == 1) {
            target = buses.get(0);
            System.out.println("Выбран автобус: " + target);
        } else {
            target = createBusForSearch();
            System.out.println("Ищем автобус: " + target);
        }
        
        System.out.println("\nВыберите метод многопоточного подсчета:");
        System.out.println("1. ExecutorService + Future (рекомендуется)");
        System.out.println("2. CompletableFuture (асинхронный)");
        System.out.println("3. ForkJoinPool (рекурсивный)");
        System.out.println("4. ReentrantLock (с блокировками)");
        System.out.println("5. Запустить бенчмарк всех методов");
        System.out.print("→ ");
        
        int methodChoice = readInt(1, 5);
        
        System.out.print("\nКоличество потоков (1-16, 0 для авто): ");
        int threadInput = readInt(0, 16);
        
        int threads = threadInput == 0 
            ? MultithreadedCounter.getRecommendedThreadCount() 
            : threadInput;
        
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, threads);
        
        switch (methodChoice) {
            case 1:
                counter.countAndPrint(target, "Future");
                break;
            case 2:
                counter.countAndPrint(target, "CompletableFuture");
                break;
            case 3:
                counter.countAndPrint(target, "ForkJoin");
                break;
            case 4:
                counter.countAndPrint(target, "Lock");
                break;
            case 5:
                System.out.println("\nЗапуск бенчмарка всех методов...");
                counter.benchmarkAllMethods(target);
                break;
        }
        
        System.out.print("\nВыполнить поиск другого автобуса? (1-Да, 2-Нет): ");
        int repeatChoice = readInt(1, 2);
        
        if (repeatChoice == 1) {
            runMultithreadedCounter(buses);
        }
    }
}
