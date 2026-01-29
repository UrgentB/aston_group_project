package org.example.Fill;

import org.example.domain.Bus;
import org.example.fill.FillBus;
import org.example.fill.FillBusRandom;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class FillRandomTest {

    private final FillBus fillBus = new FillBusRandom();

    @Test
    public void testSuccessfulFill() {
        String testInput = "3\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Bus[] buses = fillBus.fill();

            assertEquals(buses.length, 3);
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void testInvalidCountFailedFill() {
        String testInput = "13f3d1\n";

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
        String testInput = "-10\n";

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
