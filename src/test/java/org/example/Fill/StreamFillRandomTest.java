package org.example.Fill;

import org.example.domain.Bus;
import org.example.exception.InvalidUserInputException;
import org.example.fill.FillBus;
import org.example.fill.StreamFillBusRandom;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.SingletonScanner;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class StreamFillRandomTest {

    private final FillBus fillBus = new StreamFillBusRandom();

    @Test
    public void testSuccessfulFill() {
        String testInput = "3\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes())); 

        CustomList<Bus> buses = fillBus.fill();

        assertEquals(3, buses.size());
    }

    @Test
    public void testInvalidCountFailedFill() {
        String testInput = "13f3d1\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        assertThrows(InvalidUserInputException.class, () -> fillBus.fill());
    }

    @Test
    public void testNegativeCountFailedFill() {
        String testInput = "-10\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

            assertThrows(InvalidUserInputException.class, () -> fillBus.fill());
    }
}
