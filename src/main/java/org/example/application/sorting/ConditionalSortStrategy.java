package org.example.application.sorting;

import org.example.infrastructure.CustomList;

public class ConditionalSortStrategy<T> implements SortStrategy<T> {

    private final SortingPredicate<T, Integer> predicate;
    private final SortStrategy<T> sortStrategy;

    public ConditionalSortStrategy(SortStrategy<T> sortStrategy, SortingPredicate<T, Integer> predicate) {
        this.sortStrategy = sortStrategy;
        this.predicate = predicate;
    }

    @Override
    public void sort(CustomList<T> entityList) {
        CustomList<Integer> indices = new CustomList<>();
        CustomList<T> elements = new CustomList<>();
        for (int i = 0; i < entityList.size(); i++) {
            T e = entityList.get(i);
            if (predicate.execute(e, i)) {
                elements.add(e);
                indices.add(i);
            }
        }
        sortStrategy.sort(elements);
        for (int i = 0; i < indices.size(); i++) {
            entityList.set(indices.get(i), elements.get(i));
        }
    }
    
}
