package org.example.multithreading;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;


public class MultithreadedCounter<T> {
    private final List<T> collection;
    private volatile int threadCount;
    private final Lock lock = new ReentrantLock();
    private static final int FORK_JOIN_THRESHOLD = 1000;
    
    public MultithreadedCounter(List<T> collection, int threadCount) {
        if (collection == null) {
            throw new IllegalArgumentException("Коллекция не может быть null");
        }
        this.collection = collection;
        this.threadCount = Math.max(1, threadCount);
    }
    
    public int countWithFuture(T element) 
            throws InterruptedException, ExecutionException {
        if (collection.isEmpty()) return 0;
        
        int size = collection.size();
        int chunkSize = calculateChunkSize(size);
        
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CompletionService<Integer> completionService = 
            new ExecutorCompletionService<>(executor);
        
        try {
            submitTasksToExecutor(completionService, size, chunkSize, element);
            return collectResultsFromFutures(completionService);
        } finally {
            executor.shutdown();
        }
    }

    public CompletableFuture<Integer> countWithCompletableFuture(T element) {
        if (collection.isEmpty()) {
            return CompletableFuture.completedFuture(0);
        }
        
        int size = collection.size();
        int chunkSize = calculateChunkSize(size);
        
        @SuppressWarnings("unchecked")
        CompletableFuture<Integer>[] futures = new CompletableFuture[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, size);
            
            futures[i] = CompletableFuture.supplyAsync(() -> 
                countInRange(start, end, element)
            );
        }
        
        return CompletableFuture.allOf(futures)
            .thenApply(v -> {
                int total = 0;
                for (CompletableFuture<Integer> future : futures) {
                    total += future.join();
                }
                return total;
            });
    }
    
    public int countWithForkJoin(T element) {
        if (collection.isEmpty()) return 0;
        
        ForkJoinPool pool = new ForkJoinPool(threadCount);
        try {
            CountingTask task = new CountingTask(collection, element, 0, collection.size());
            return pool.invoke(task);
        } finally {
            pool.shutdown();
        }
    }
    
    public int countWithLock(T element) throws InterruptedException {
        if (collection.isEmpty()) return 0;
        
        int size = collection.size();
        int chunkSize = calculateChunkSize(size);
        
        AtomicInteger totalCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, size);
            
            new Thread(() -> {
                int localCount = countInRange(start, end, element);
                
                lock.lock();
                try {
                    totalCount.addAndGet(localCount);
                } finally {
                    lock.unlock();
                }
                
                latch.countDown();
            }).start();
        }
        
        latch.await();
        return totalCount.get();
    }
    
    public int countOccurrences(T element) {
        try {
            return countWithFuture(element);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            System.err.println("Подсчет был прерван: " + e.getMessage());
            return 0;
        }
    }
    
     //* Подсчитывает и выводит результат в консоль с выбором метода

    public void countAndPrint(T element, String method) {
        printHeader(method);
        
        System.out.println("Размер коллекции: " + collection.size());
        System.out.println("Используется потоков: " + threadCount);
        System.out.println("Искомый элемент: " + element);
        
        long startTime = System.nanoTime();
        int count = executeCountingMethod(element, method);
        long endTime = System.nanoTime();
        
        printResults(count, startTime, endTime);
    }
    
    //Запускает все методы и сравнивает их производительность
    public void benchmarkAllMethods(T element) {
        printBenchmarkHeader();
        
        String[] methods = {"Future", "CompletableFuture", "ForkJoin", "Lock"};
        
        for (String method : methods) {
            countAndPrint(element, method);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ 
    
    private int calculateChunkSize(int totalSize) {
        return (totalSize + threadCount - 1) / threadCount;
    }
    
    private int countInRange(int start, int end, T element) {
        int localCount = 0;
        for (int i = start; i < end; i++) {
            T current = collection.get(i);
            if (current != null && current.equals(element)) {
                localCount++;
            }
        }
        return localCount;
    }
    
    private void submitTasksToExecutor(
            CompletionService<Integer> completionService,
            int totalSize, int chunkSize, T element) {
        
        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, totalSize);
            
            completionService.submit(() -> countInRange(start, end, element));
        }
    }
    
    private int collectResultsFromFutures(CompletionService<Integer> completionService) 
            throws InterruptedException, ExecutionException {
        
        int totalCount = 0;
        for (int i = 0; i < threadCount; i++) {
            Future<Integer> future = completionService.take();
            totalCount += future.get();
        }
        return totalCount;
    }
    
    private int executeCountingMethod(T element, String method) {
        try {
            switch (method) {
                case "Future":
                    return countWithFuture(element);
                case "CompletableFuture":
                    return countWithCompletableFuture(element).get();
                case "ForkJoin":
                    return countWithForkJoin(element);
                case "Lock":
                    return countWithLock(element);
                default:
                    return countOccurrences(element);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при подсчете: " + e.getMessage());
            return 0;
        }
    }
    
    private void printHeader(String method) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("МНОГОПОТОЧНЫЙ ПОДСЧЕТ ВХОЖДЕНИЙ");
        System.out.println("Метод: " + method);
        System.out.println("=".repeat(60));
    }
    
    private void printBenchmarkHeader() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("БЕНЧМАРК МЕТОДОВ МНОГОПОТОЧНОГО ПОДСЧЕТА");
        System.out.println("=".repeat(70));
    }
    
    private void printResults(int count, long startTime, long endTime) {
        double elapsedMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("\nРЕЗУЛЬТАТЫ:");
        System.out.println("-".repeat(40));
        System.out.println("Найдено вхождений: " + count);
        System.out.printf("Время выполнения: %.3f мс\n", elapsedMs);
        System.out.println("=".repeat(60) + "\n");
    }

    private class CountingTask extends RecursiveTask<Integer> {
        private final List<T> list;
        private final T element;
        private final int start;
        private final int end;
        
        CountingTask(List<T> list, T element, int start, int end) {
            this.list = list;
            this.element = element;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Integer compute() {
            if (end - start <= FORK_JOIN_THRESHOLD) {
                return countInRange(start, end, element);
            }
            
            int mid = (start + end) / 2;
            CountingTask leftTask = new CountingTask(list, element, start, mid);
            CountingTask rightTask = new CountingTask(list, element, mid, end);
            
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            
            return leftResult + rightResult;
        }
    }
    
    //Возвращает рекомендуемое количество потоков
    public static int getRecommendedThreadCount() {
        int processors = Runtime.getRuntime().availableProcessors();
        return Math.max(2, processors);
    }
    
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
