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
    SORT_NUMBER("Сортировать по номеру.", new NumberBusComparator()),
    /**
     * Sorting by model.
     */
    SORT_MODEL("Сортировать по модели.", new ModelBusComparator()),
    /**
     * Sorting by mileage.
     */
    SORT_MILEAGE("Сортировать по пробегу.", new MileageBusComparator()),
    /**
     * Sort all fields (basic)
     */
    SORT_BASIC("Сортировать по всем полям.", new BasicBusComparator());

    private final String title;
    private final Comparator<Bus> comparator;

    SortType(String title, Comparator<Bus> comparator) {
        this.title = title;
        this.comparator = comparator;
    }

    public String getTitle() {
        return title;
    }

    public Comparator<Bus> geComparator() {
        return comparator;
    }
}
