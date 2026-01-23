package org.example.infrastructure;

import org.example.application.ExportService;
import org.example.application.ImportService;
import org.example.domain.Bus;

public class BusFileStorageImpl implements FileStorage<Bus> {

    private final ExportService<Bus> busExportService;
    private final ImportService<Bus> busImportService;

    public BusFileStorageImpl(ExportService<Bus> busExportService, ImportService<Bus> busImportService) {
        this.busExportService = busExportService;
        this.busImportService = busImportService;
    }


    @Override
    public void save(CustomList<Bus> data) {
        busExportService.save(data);
    }

    @Override
    public CustomList<Bus> load() {
        return busImportService.load();
    }
    
}
