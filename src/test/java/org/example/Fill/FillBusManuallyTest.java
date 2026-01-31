package org.example.Fill;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.domain.Bus;
import org.example.fill.FillBus;
import org.example.fill.FillBusManually;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FillBusManuallyTest {

    private final FillBus fillBus = new FillBusManually();

    @Test
    public void testSuccessfulOneFill() {
        String testInput = "1\n101\nVolvo\n15000,5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            Bus busResult = Bus.createInstance(101,"Volvo", 15000.5);

            assertEquals(buses[0], busResult);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testSuccessfulLotFill() {
        String testInput = "3\n101\nMercedes\n15000,5\n102\nVolvo\n20000,0\n103\nMercedes\n14444\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            Bus busResult1 = Bus.createInstance(101,"Mercedes", 15000.5);
            Bus busResult2 = Bus.createInstance(102,"Volvo", 20000.0);
            Bus busResult3 = Bus.createInstance(103,"Mercedes", 14444);

            assertEquals(buses[0], busResult1);
            assertEquals(buses[1], busResult2);
            assertEquals(buses[2], busResult3);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testSuccessfulOneOfTwoFill() {
        String testInput = "2\n101\nMercedes\n15000,5\n-102\nVolvo\n20000,0\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            Bus busResult1 = Bus.createInstance(101,"Mercedes", 15000.5);

            assertEquals(buses[0], busResult1);
            assertEquals(buses.length, 1);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testInvalidCountFailedFill() {
        String testInput = "d1d\n101\nMercedes\n15000,5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertNull(buses);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testNegativeCountFailedFill() {
        String testInput = "-1\n101\nMercedes\n15000,5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertNull(buses);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testInvalidNumberFailedFill() {
        String testInput = "1\n10d1\nMercedes\n15000,5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertNull(buses);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testInvalidMileageFailedFill() {
        String testInput = "1\n101\nMercedes\n15000.5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertNull(buses);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testNegativeMileageFailedFill() {
        String testInput = "1\n101\nMercedes\n-15000,5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertNull(buses);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testEmptyModelFailedFill() {
        String testInput = "1\n101\n \n15000,5\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertNull(buses);
        } finally {
            System.setIn(originalIn);
        }
    }
}
