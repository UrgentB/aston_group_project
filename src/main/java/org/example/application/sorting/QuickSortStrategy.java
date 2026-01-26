package org.example.application.sorting;

import java.util.Comparator;

import org.example.infrastructure.CustomList;

public class QuickSortStrategy<T> implements SortStrategy<T> {

    private final Comparator<T> comparator;

    public QuickSortStrategy(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void sort(CustomList<T> entityList) {
        quicksort(entityList, 0, entityList.size()-1);
    }

    private void quicksort(CustomList<T> entityList, int startIdx, int endIdx) {
        if (startIdx < endIdx) {
            int pivotIdx = partition(entityList, startIdx, endIdx);
            quicksort(entityList, startIdx, pivotIdx - 1);
            quicksort(entityList, pivotIdx + 1, endIdx);
        }
    }

    private int partition(CustomList<T> entityList, int startIdx, int endIdx) {
        T pivot = entityList.get(startIdx);
        int leftIdx = startIdx + 1;
        int rightIdx = endIdx;
        while (true) {
            while (leftIdx <= rightIdx && comparator.compare(entityList.get(leftIdx), pivot) <= 0) {
                leftIdx++;
            }
            while (leftIdx <= rightIdx && comparator.compare(pivot, entityList.get(rightIdx)) <= 0) {
                rightIdx--;
            }
            if (leftIdx > rightIdx) {
                break;
            } else {
                T temp = entityList.get(leftIdx);
                entityList.set(leftIdx, entityList.get(rightIdx));
                entityList.set(rightIdx, temp);
            }
        }
        T temp = entityList.get(startIdx);
        entityList.set(startIdx, entityList.get(rightIdx));
        entityList.set(rightIdx, temp);
        return rightIdx;
    }
    
}
