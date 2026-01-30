package org.example.application;

import java.io.IOException;
import java.util.Comparator;

import org.example.application.sorting.SortAlgorithm;
import org.example.application.sorting.SortCondition;
import org.example.application.sorting.SortStrategy;
import org.example.application.sorting.SortType;
import org.example.domain.Bus;
import org.example.exception.BusServiceException;
import org.example.infrastructure.CustomList;

/**
 * Implementation of Service interface for Bus objects.
 */
public class BusServiceImpl implements Service<Bus> {

    private final ImportService<Bus> importService;
    private final ExportService<Bus> exportService;

    public BusServiceImpl(ImportService<Bus> importService, ExportService<Bus> exportService) {
        this.importService = importService;
        this.exportService = exportService;
    }

    @Override
    public CustomList<Bus> sort(CustomList<Bus> data, SortType sortType, SortAlgorithm sortAlgorithm, SortCondition sortCondition) {
        Comparator<Bus> comparator = sortType.geComparator();
        SortStrategy<Bus> sortStrategy = sortAlgorithm.create(comparator);
        sortStrategy.sort(data);
        return data;
    }

    @Override
    public CustomList<Bus> read(InputType inputType) {
        //importService.setStrategy(inputType);
        try {
            return importService.load();
        } catch (IOException e) {
            throw new BusServiceException("Input error!");
        }
    }

    @Override
    public void write(CustomList<Bus> data) {
        exportService.save(data);
    }
    
}
