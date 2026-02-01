package org.example.application;

import java.io.IOException;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

public class FileBusInputStrategyAdapter implements InputStrategy<Bus> {

    private final ImportService<Bus> importService;

    public FileBusInputStrategyAdapter() {
        this.importService = BusImportService.getInstance();
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
