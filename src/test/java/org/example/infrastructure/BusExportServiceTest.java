package org.example.infrastructure;

import org.example.application.BusExportService;
import org.example.domain.Bus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;

class BusExportServiceTest {

    @TempDir
    Path tempDir;

    private BusExportService exportService;
    private CustomList<Bus> busList;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_buses.txt").toString();
        busList = new CustomList<>();
    }

    @Test
    @DisplayName("Сохранение данных в файл с режимом перезаписи")
    void testSaveWithOverwriteMode() throws IOException {
        exportService = new BusExportService(false, testFilePath);
        Bus bus1 = new Bus(111, "Volvo", 12451D);
        Bus bus2 = new Bus(123, "Паз", 415125D);
        busList.add(bus1);
        busList.add(bus2);

        exportService.save(busList);

        String fileContent = Files.readString(Path.of(testFilePath));
        assertTrue(fileContent.contains("111"));
        assertTrue(fileContent.contains("Volvo"));
        assertTrue(fileContent.contains("12451"));
        assertTrue(fileContent.contains("Паз"));
        assertEquals(2, fileContent.lines().count());
    }

    @Test
    @DisplayName("Сохранение данных в файл с режимом дополнения")
    void testSaveWithAppendMode() throws IOException {
        exportService = new BusExportService(true, testFilePath);
        Bus bus1 = new Bus(111, "Volvo", 12451D);
        busList.add(bus1);
        exportService.save(busList);

        CustomList<Bus> additionalList = new CustomList<>();
        Bus bus2 = new Bus(123, "Паз", 415125D);
        additionalList.add(bus2);
        exportService.save(additionalList);

        String fileContent = Files.readString(Path.of(testFilePath));
        long lineCount = fileContent.lines().count();
        assertEquals(2, lineCount);
        assertTrue(fileContent.contains("111"));
        assertTrue(fileContent.contains("123"));
    }

    @Test
    @DisplayName("Сохранение пустого списка")
    void testSaveEmptyList() throws IOException {
        exportService = new BusExportService(false, testFilePath);

        exportService.save(busList);

        String fileContent = Files.readString(Path.of(testFilePath));
        assertTrue(fileContent.isEmpty());
    }

    @Test
    @DisplayName("Обработка IOException при записи")
    void testSaveWithIOException() {
        String invalidPath = "/invalid/path/buses.txt";
        exportService = new BusExportService(false, invalidPath);
        Bus bus = new Bus(123, "Паз", 415125D);
        busList.add(bus);

        assertDoesNotThrow(() -> exportService.save(busList));
    }

    @Test
    @DisplayName("Проверка корректности форматирования данных")
    void testDataFormatting() throws IOException {
        exportService = new BusExportService(false, testFilePath);
        Bus bus = new Bus(123, "Паз", 415125D);
        busList.add(bus);

        exportService.save(busList);

        String fileContent = Files.readString(Path.of(testFilePath)).trim();
        assertEquals(bus.toString(), fileContent);
    }

    @Test
    @DisplayName("Сохранение большого количества данных")
    void testSaveLargeDataSet() throws IOException {
        exportService = new BusExportService(false, testFilePath);
        int count = 1000;
        for (int i = 0; i < count; i++) {
            busList.add(new Bus(i, "Паз_" + i,  12000D + i));
        }

        exportService.save(busList);

        long lineCount = Files.lines(Path.of(testFilePath)).count();
        assertEquals(count, lineCount);
    }
}