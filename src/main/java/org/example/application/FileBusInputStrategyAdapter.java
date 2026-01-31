package org.example.application;

import java.io.IOException;

import org.example.Read_From_File.ReadFromTxtFile;
import org.example.Validator.BusValidator;
import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

public class FileBusInputStrategyAdapter implements InputStrategy<Bus> {

    private final Readable readFromFile = new ReadFromTxtFile();
    private final BusValidator busValidator = new BusValidator();
    private String path = "/";
    private final ImportService<Bus> importService;

    public FileBusInputStrategyAdapter() {
        importService = new BusImportService(readFromFile, busValidator, path);
    }

    @Override
    public CustomList<Bus> loadData() throws IOException {
        return importService.load();
    }

    @Override
    public CustomList<Bus> loadStreamData() throws IOException {
        return importService.streamLoad();
    }
    
}
