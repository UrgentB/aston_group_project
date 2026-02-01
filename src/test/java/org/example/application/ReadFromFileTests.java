package org.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.example.Read_From_File.ReadFromTxtFile;
import org.example.Validator.BusValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ReadFromFileTests {

    @TempDir
    Path tempDir;

    private String testFilePath;
    
    @Test
    public void testPositiveReadFromFile() throws IOException {
        testFilePath = tempDir.resolve("test_import.txt").toString();
        String fileContent = """
            111, Volvo, 12451.1
            123, Паз, 415125.2
            54, Маз, 25512.7
            """;
        Files.writeString(Path.of(testFilePath), fileContent);

        Readable readable = new ReadFromTxtFile();
        List<String> strings = readable.read(testFilePath);

        assertEquals(fileContent, strings.stream().collect(Collectors.joining("\n")) + "\n");
    }
}
