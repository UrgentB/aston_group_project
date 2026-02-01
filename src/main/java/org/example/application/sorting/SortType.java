package org.example.application.sorting;

import java.util.Comparator;

import org.example.application.comparators.BasicBusComparator;
import org.example.application.comparators.MileageBusComparator;
import org.example.application.comparators.ModelBusComparator;
import org.example.application.comparators.NumberBusComparator;
import org.example.domain.Bus;

/**
 * Enumeration representing keys for different types of sorting methods.
 */
public enum SortType {
    /**
     * Sorting by number.
     */
    SORT_NUMBER(new NumberBusComparator()),
    /**
     * Sorting by model.
     */
    SORT_MODEL(new ModelBusComparator()),
    /**
     * Sorting by mileage.
     */
    SORT_MILEAGE(new MileageBusComparator()),
    /**
     * Sort all fields (basic)
     */
    SORT_BASIC(new BasicBusComparator());

    private final Comparator<Bus> comparator;

    SortType(Comparator<Bus> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Bus> getComparator() {
        return comparator;
    }
}
