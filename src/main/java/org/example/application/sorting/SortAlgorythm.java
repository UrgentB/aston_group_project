package org.example.application.sorting;

import java.util.Comparator;
import java.util.function.Function;

import org.example.domain.Bus;

public enum SortAlgorythm {
    SORT_BUBBLE(BubbleSortStrategy::new),
    SORT_QUICKSORT(QuickSortStrategy::new),
    SORT_SELECTION(SelectionSortStrategy::new),
    SORT_CONDITION(BubbleSortStrategy::new);

    private Function<Comparator<Bus>, SortStrategy<Bus>> factory;

    private SortAlgorythm(Function<Comparator<Bus>, SortStrategy<Bus>> factory) {
        this.factory = factory;
    }

    public SortStrategy<Bus> create(Comparator<Bus> conparator) {
        return factory.apply(conparator);
    }
}
