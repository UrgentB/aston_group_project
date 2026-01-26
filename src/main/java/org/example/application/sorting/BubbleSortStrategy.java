package org.example.application.sorting;

import java.util.Comparator;

import org.example.infrastructure.CustomList;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    private final Comparator<T> comparator;

    public BubbleSortStrategy(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void sort(CustomList<T> entityList) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for(int i = 1; i < entityList.size(); i++) {
                T entity1 = entityList.get(i - 1);
                T entity2 = entityList.get(i);
                if (comparator.compare(entity1, entity2) > 0) {
                    isSorted = false;
                    entityList.set(i - 1, entity2);
                    entityList.set(i, entity1);
                }
            }
        }
    }
    
}
