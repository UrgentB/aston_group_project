package org.example.application;

import java.io.IOException;
import java.util.Comparator;

import org.example.application.sorting.ConditionalSortStrategy;
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

    private final ExportService<Bus> exportService;

    public BusServiceImpl(ExportService<Bus> exportService) {
        this.exportService = exportService;
    }

    @Override
    public CustomList<Bus> sort(CustomList<Bus> data, SortType sortType, SortAlgorithm sortAlgorithm, SortCondition sortCondition) {
        Comparator<Bus> comparator = sortType.getComparator();
        SortStrategy<Bus> sortStrategy = sortAlgorithm.create(comparator);
        new ConditionalSortStrategy<Bus>(sortStrategy, sortCondition.getPredicate()).sort(data);
        return data;
    }

    @Override
    public CustomList<Bus> read(InputType inputType) {
        try {
            return inputType.getStrategy().loadData();
        } catch (IOException e) {
            throw new BusServiceException("Input error!");
        }
    }

    @Override
    public CustomList<Bus> streamRead(InputType inputType) {
        try {
            return inputType.getStrategy().loadStreamData();
        } catch (IOException e) {
            throw new BusServiceException("Input error!");
        }
    }

    @Override
    public void write(CustomList<Bus> data) {
        exportService.save(data);
    }
    
}
