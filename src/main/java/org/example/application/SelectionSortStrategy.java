package org.example.application;

import java.util.Comparator;

import org.example.infrastructure.CustomList;

public class SelectionSortStrategy<T> extends SortStrategy<T> {

    protected SelectionSortStrategy(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    void sort(CustomList<T> entityList) {
        int sortedIndex = 0;
        while (sortedIndex < entityList.size() - 1) {
            int minIndex = sortedIndex;
            for (int i = sortedIndex; i < entityList.size(); i++) {
                if(comparator.compare(entityList.get(i), entityList.get(minIndex)) < 0) {
                    minIndex = i;
                }
            }
            T current = entityList.get(sortedIndex);
            T min = entityList.get(minIndex);
            entityList.set(minIndex, current);
            entityList.set(sortedIndex, min);
            sortedIndex++;
        }
    }
}
