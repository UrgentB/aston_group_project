package org.example.multithreading;

import org.example.domain.Bus;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

class MultithreadedCounterTest {
    
    @Test
    void testCountWithFuture() throws ExecutionException, InterruptedException {
        List<Bus> buses = Arrays.asList(
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(102, "Volvo", 20000.0),
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(103, "MAN", 12000.0),
            Bus.createInstance(101, "Mercedes", 15000.5)
        );
        
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, 2);
        
        int result = counter.countWithFuture(target);
        assertEquals(3, result);
    }
    
    @Test
    void testCountWithCompletableFuture() throws ExecutionException, InterruptedException {
        List<Bus> buses = Arrays.asList(
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(102, "Volvo", 20000.0),
            Bus.createInstance(101, "Mercedes", 15000.5)
        );
        
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, 2);
        
        int result = counter.countWithCompletableFuture(target).get();
        assertEquals(2, result);
    }
    
    @Test
    void testCountWithForkJoin() {
        List<Bus> buses = Arrays.asList(
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(102, "Volvo", 20000.0),
            Bus.createInstance(103, "MAN", 12000.0),
            Bus.createInstance(101, "Mercedes", 15000.5)
        );
        
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, 2);
        
        int result = counter.countWithForkJoin(target);
        assertEquals(2, result);
    }
    
    @Test
    void testCountWithLock() throws InterruptedException {
        List<Bus> buses = Arrays.asList(
            Bus.createInstance(101, "Mercedes", 15000.5),
            Bus.createInstance(102, "Volvo", 20000.0),
            Bus.createInstance(101, "Mercedes", 15000.5)
        );
        
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, 2);
        
        int result = counter.countWithLock(target);
        assertEquals(2, result);
    }
    
    @Test
    void testEmptyCollection() throws ExecutionException, InterruptedException {
        List<Bus> emptyList = Arrays.asList();
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(emptyList, 2);
        
        assertEquals(0, counter.countWithFuture(target));
        assertEquals(0, counter.countWithCompletableFuture(target).get());
        assertEquals(0, counter.countWithForkJoin(target));
        assertEquals(0, counter.countWithLock(target));
    }
    
    @Test
    void testNullSafety() {
        List<Bus> buses = Arrays.asList(
            Bus.createInstance(101, "Mercedes", 15000.5),
            null,
            Bus.createInstance(101, "Mercedes", 15000.5)
        );
        
        Bus target = Bus.createInstance(101, "Mercedes", 15000.5);
        MultithreadedCounter<Bus> counter = new MultithreadedCounter<>(buses, 2);
        
        assertDoesNotThrow(() -> {
            counter.countWithFuture(target);
            counter.countWithCompletableFuture(target);
            counter.countWithForkJoin(target);
            counter.countWithLock(target);
        });
    }
}
