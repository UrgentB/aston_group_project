package org.example.application.sorting;

import java.util.Comparator;
import java.util.function.Function;

import org.example.domain.Bus;

public enum SortAlgorithm {
    SORT_BUBBLE(BubbleSortStrategy::new),
    SORT_QUICKSORT(QuickSortStrategy::new),
    SORT_SELECTION(SelectionSortStrategy::new);

    private Function<Comparator<Bus>, SortStrategy<Bus>> factory;

    private SortAlgorithm(Function<Comparator<Bus>, SortStrategy<Bus>> factory) {
        this.factory = factory;
    }

    public SortStrategy<Bus> create(Comparator<Bus> comparator) {
        return factory.apply(comparator);
    }
}
