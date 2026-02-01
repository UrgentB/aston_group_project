package org.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.example.Validator.BusValidator;
import org.example.domain.Bus;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.SingletonScanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputStrategyTests {

    @TempDir
    Path tempDir;

    private BusImportService importService;
    private Readable readableMock;
    private BusValidator busValidatorMock;
    private String testFilePath;
    
    @Test
    public void testPositiveManualBusInputStrategyAdapter() throws IOException {
        InputStrategy<Bus> inputStrategy = InputType.INPUT_MANUAL.getStrategy();
        String testInput = "1\n101\nVolvo\n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));
        
        CustomList<Bus> buses = inputStrategy.loadData();

        Bus busResult = Bus.createInstance(101,"Volvo", 15000.5);
        assertEquals(busResult, buses.get(0));
    }

    @Test
    public void testPositiveManualBusInputStrategyAdapterStreams() throws IOException {
        InputStrategy<Bus> inputStrategy = InputType.INPUT_MANUAL.getStrategy();
        String testInput = "1\n101\nVolvo\n15000,5\n";

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = inputStrategy.loadStreamData();

        Bus busResult = Bus.createInstance(101, "Volvo", 15000.5);
        assertEquals(busResult, buses.get(0));

    }

    @Test
    public void testPositiveRandomBusInputStrategyAdapter() throws IOException {
        String testInput = "3\n";
        InputStrategy<Bus> inputStrategy = InputType.INPUT_RANDOM.getStrategy();

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = inputStrategy.loadData();

        assertEquals(3, buses.size());

    }

    @Test
    public void testPositiveRandomBusInputStrategyAdapterStreams() throws IOException {
        String testInput = "3\n";
        InputStrategy<Bus> inputStrategy = InputType.INPUT_RANDOM.getStrategy();

        SingletonScanner.reset(new ByteArrayInputStream(testInput.getBytes()));

        CustomList<Bus> buses = inputStrategy.loadData();

        assertEquals(3, buses.size());

    }

    @Test
    public void testPositiveFileBusInputStrategyAdapterStreams() throws IOException {
        importService = BusImportService.getInstance();
        importService.reset();
        testFilePath = tempDir.resolve("test_import.txt").toString();
        readableMock = mock(Readable.class);
        busValidatorMock = mock(BusValidator.class);
        importService.setReadable(readableMock);
        importService.setValidator(busValidatorMock);
        importService.setPath(testFilePath);
        InputStrategy<Bus> inputStrategy = InputType.INPUT_FROM_FILE.getStrategy();
        String fileContent = """
                111, Volvo, 12451.1
                123, Паз, 415125.2
                54, Маз, 25512.7
                """;
        Files.writeString(Path.of(testFilePath), fileContent);

        Bus bus1 = new Bus(111, "Volvo", 12451.1);
        Bus bus2 = new Bus(123, "Паз", 415125.2);
        Bus bus3 = new Bus(54, "Маз", 25512.7);

        when(busValidatorMock.parcer("111, Volvo, 12451.1")).thenReturn(bus1);
        when(busValidatorMock.parcer("123, Паз, 415125.2")).thenReturn(bus2);
        when(busValidatorMock.parcer("54, Маз, 25512.7")).thenReturn(bus3);

        CustomList<Bus> result = inputStrategy.loadStreamData();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(bus1, result.get(0));
        assertEquals(bus2, result.get(1));
        assertEquals(bus3, result.get(2));

        verify(busValidatorMock, times(3)).parcer(anyString());
    }

    @Test
    public void testPositiveFileBusInputStrategyAdapter() throws IOException {
        testFilePath = tempDir.resolve("test_import.txt").toString();
        readableMock = mock(Readable.class);
        busValidatorMock = mock(BusValidator.class);
        importService = BusImportService.getInstance();
        importService.reset();
        importService.setReadable(readableMock);
        importService.setValidator(busValidatorMock);
        importService.setPath(testFilePath);
        InputStrategy<Bus> inputStrategy = InputType.INPUT_FROM_FILE.getStrategy();
        List<String> lines = Arrays.asList(
                "111, Volvo, 12451.1",
                "123, Паз, 415125.2"
        );
        Bus bus1 = new Bus(111, "Volvo", 12451D);
        Bus bus2 = new Bus(123, "Паз", 415125D);

        when(readableMock.read(testFilePath)).thenReturn(lines);
        when(busValidatorMock.parcer("111, Volvo, 12451.1")).thenReturn(bus1);
        when(busValidatorMock.parcer("123, Паз, 415125.2")).thenReturn(bus2);

        CustomList<Bus> result = inputStrategy.loadData();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(bus1, result.get(0));
        assertEquals(bus2, result.get(1));

        verify(readableMock, times(1)).read(testFilePath);
        verify(busValidatorMock, times(2)).parcer(anyString());
    }

    @Test
    void testPositiveInputTypeValues() {
        assertEquals(3, InputType.values().length);
        assertEquals(InputType.INPUT_RANDOM,
                InputType.valueOf("INPUT_RANDOM"));
    }
}
