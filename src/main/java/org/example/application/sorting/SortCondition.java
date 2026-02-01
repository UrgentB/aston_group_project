package org.example.application.sorting;

import org.example.domain.Bus;

public enum SortCondition {
    SORT_ODD_ONLY((bus, index) -> index % 2 == 1),
    SORT_EVEN_ONLY((bus, index) -> index % 2 == 0),
    SORT_ALL((bus, index) -> true);

    private SortingPredicate<Bus, Integer> predicate;

    private SortCondition(SortingPredicate<Bus, Integer> predicate) {
        this.predicate = predicate;
    }

    public SortingPredicate<Bus, Integer> getPredicate() {
        return predicate;
    }
}
