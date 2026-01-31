package org.example.Fill;

import org.example.domain.Bus;
import org.example.exception.InvalidUserInputException;
import org.example.fill.FillBus;
import org.example.fill.FillBusRandom;
import org.example.infrastructure.CustomList;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FillRandomTest {

    private final FillBus fillBus = new FillBusRandom();

    @Test
    public void testSuccessfulFill() {
        String testInput = "3\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            CustomList<Bus> buses = fillBus.fill();

            assertEquals(3, buses.size());
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

            assertThrows(InvalidUserInputException.class, () -> fillBus.fill());
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

            CustomList<Bus> buses = fillBus.fill();

            assertEquals(0, buses.size());
        } finally {
            System.setIn(originalIn);
        }
    }
}
