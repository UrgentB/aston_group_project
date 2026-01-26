package org.example.application;

import java.util.Comparator;

import org.example.application.sorting.SortAlgorythm;
import org.example.application.sorting.SortStrategy;
import org.example.application.sorting.SortType;
import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

/**
 * Implementation of Service interface for Bus objects.
 */
public class BusServiceImpl implements Service<Bus> {

    @Override
    public CustomList<Bus> sort(CustomList<Bus> data, SortType sortType, SortAlgorythm sortAlgorythm) {
        Comparator<Bus> comparator = sortType.geComparator();
        SortStrategy<Bus> sortStrategy = sortAlgorythm.create(comparator);
        sortStrategy.sort(data);
        return data;
    }

    @Override
    public CustomList<Bus> read(InputType inputType) {
        // Implementation of reading logic for Bus objects based on input type
        return null;
    }

    @Override
    public void write(CustomList<Bus> data) {
        // Implementation of writing logic for Bus objects
    }
    
}
