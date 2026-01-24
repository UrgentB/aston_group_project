package org.example.application;

import java.util.Comparator;

import org.example.infrastructure.CustomList;

public class BubbleSortStrategy<T> extends SortStrategy<T> {

    protected BubbleSortStrategy(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    void sort(CustomList<T> entityList) {
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
