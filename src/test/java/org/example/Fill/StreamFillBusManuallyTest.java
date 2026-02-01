package org.example.Fill;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.example.domain.Bus;
import org.example.exception.InvalidUserInputException;
import org.example.fill.FillBus;
import org.example.fill.StreamFillBusManually;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.SingletonScanner;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class StreamFillBusManuallyTest {

    private final FillBus fillBus = new StreamFillBusManually();

    @Test
    public void testSuccessfulOneFill() {
        String testInput = "1\n101\nVolvo\n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        Bus busResult = Bus.createInstance(101,"Volvo", 15000.5);

        assertEquals(busResult, buses.get(0));
    }

    @Test
    public void testSuccessfulLotFill() {
        String testInput = "3\n101\nMercedes\n15000,5\n102\nVolvo\n20000,0\n103\nMercedes\n14444\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        Bus busResult1 = Bus.createInstance(101,"Mercedes", 15000.5);
        Bus busResult2 = Bus.createInstance(102,"Volvo", 20000.0);
        Bus busResult3 = Bus.createInstance(103,"Mercedes", 14444);

        assertEquals(busResult1, buses.get(0));
        assertEquals(busResult2, buses.get(1));
        assertEquals(busResult3, buses.get(2));
        assertEquals(3, buses.size());
    }

    @Test
    public void testSuccessfulOneOfTwoFill() {
        String testInput = "2\n101\nMercedes\n15000,5\n-102\nVolvo\n20000,0\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        Bus busResult1 = Bus.createInstance(101,"Mercedes", 15000.5);

        assertEquals(busResult1, buses.get(0));
        assertEquals(1, buses.size());
    }

    @Test
    public void testInvalidCountFailedFill() {
        String testInput = "d1d\n101\nMercedes\n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        assertThrows(InvalidUserInputException.class, () -> fillBus.fill());
    }

    @Test
    public void testNegativeCountFailedFill() {
        String testInput = "-1\n101\nMercedes\n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        assertThrows(InvalidUserInputException.class, () -> fillBus.fill());
    }

    @Test
    public void testInvalidNumberFailedFill() {
        String testInput = "1\n10d1\nMercedes\n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        assertNull(buses);
    }

    @Test
    public void testInvalidMileageFailedFill() {
        String testInput = "1\n101\nMercedes\n15000.5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        assertNull(buses);
    }

    @Test
    public void testNegativeMileageFailedFill() {
        String testInput = "1\n101\nMercedes\n-15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        assertNull(buses);
    }

    @Test
    public void testEmptyModelFailedFill() {
        String testInput = "1\n101\n \n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = fillBus.fill();

        assertNull(buses);
    }
}
