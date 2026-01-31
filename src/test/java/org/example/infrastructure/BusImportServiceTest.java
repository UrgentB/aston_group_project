package org.example.infrastructure;

import org.example.Validator.BusValidator;
import org.example.application.BusImportService;
import org.example.application.Readable;
import org.example.domain.Bus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

class BusImportServiceTest {

    @TempDir
    Path tempDir;

    private BusImportService importService;
    private Readable readableMock;
    private BusValidator busValidatorMock;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_import.txt").toString();
        readableMock = mock(Readable.class);
        busValidatorMock = mock(BusValidator.class);
        importService = new BusImportService(readableMock, busValidatorMock, testFilePath);
    }

    @Test
    @DisplayName("Загрузка данных из файла")
    void testLoad() throws IOException {
        List<String> lines = Arrays.asList(
                "111, Volvo, 12451.1",
                "123, Паз, 415125.2"
        );
        Bus bus1 = new Bus(111, "Volvo", 12451D);
        Bus bus2 = new Bus(123, "Паз", 415125D);

        when(readableMock.read(testFilePath)).thenReturn(lines);
        when(busValidatorMock.parcer("111, Volvo, 12451.1")).thenReturn(bus1);
        when(busValidatorMock.parcer("123, Паз, 415125.2")).thenReturn(bus2);

        CustomList<Bus> result = importService.load();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(bus1, result.get(0));
        assertEquals(bus2, result.get(1));

        verify(readableMock, times(1)).read(testFilePath);
        verify(busValidatorMock, times(2)).parcer(anyString());
    }

    @Test
    @DisplayName("Загрузка пустого файла")
    void testLoadEmptyFile() throws IOException {
        when(readableMock.read(testFilePath)).thenReturn(Collections.emptyList());

        CustomList<Bus> result = importService.load();

        assertNotNull(result);
        verify(readableMock, times(1)).read(testFilePath);
        verify(busValidatorMock, never()).parcer(anyString());
    }

    @Test
    @DisplayName("Обработка IOException при загрузке")
    void testLoadWithIOException() throws IOException {
        when(readableMock.read(testFilePath)).thenThrow(new IOException("File not found"));

        assertThrows(RuntimeException.class, () -> importService.load());
        verify(readableMock, times(1)).read(testFilePath);
    }

    @Test
    @DisplayName("Stream загрузка данных")
    void testStreamLoad() throws IOException {
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

        CustomList<Bus> result = importService.streamLoad();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(bus1, result.get(0));
        assertEquals(bus2, result.get(1));
        assertEquals(bus3, result.get(2));

        verify(busValidatorMock, times(3)).parcer(anyString());
    }

    @Test
    @DisplayName("Stream загрузка пустого файла")
    void testStreamLoadEmptyFile() throws IOException {
        Files.createFile(Path.of(testFilePath));

        CustomList<Bus> result = importService.streamLoad();

        assertNotNull(result);
        verify(busValidatorMock, never()).parcer(anyString());
    }

    @Test
    @DisplayName("Stream загрузка с некорректными данными")
    void testStreamLoadWithInvalidData() throws IOException {
        String fileContent = """
            111, Volvo, 12451.1
            INVALID_DATA
            123, Паз, 415125.2
            """;
        Files.writeString(Path.of(testFilePath), fileContent);

        Bus bus1 = new Bus(111, "Volvo", 12451.1);
        Bus bus2 = new Bus(123, "Паз", 415125.2);

        when(busValidatorMock.parcer("111, Volvo, 12451.1")).thenReturn(bus1);
        when(busValidatorMock.parcer("INVALID_DATA")).thenReturn(null);
        when(busValidatorMock.parcer("123, Паз, 415125.2")).thenReturn(bus2);

        CustomList<Bus> result = importService.streamLoad();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertNull(result.get(1));
        verify(busValidatorMock, times(3)).parcer(anyString());
    }
}
