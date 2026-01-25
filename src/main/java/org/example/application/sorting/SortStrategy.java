package org.example.application.sorting;

import java.util.Comparator;

import org.example.infrastructure.CustomList;

/**
 * Generic strategy abstraction for sorting a CustomList.
 * @param <T> the type of elements in the CustomList
 */

public abstract class SortStrategy<T> {

    /**
     * Comparator used for sorting.
     */

    protected Comparator<T> comparator;

    /**
     * Constructor accepting a comparator.
     * @param comparator
     */

    protected SortStrategy(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Sorts the given CustomList.
     * Поле, которое нужно отсортировать, определяется компаратором.
     * @param entityList
     */

    public abstract void sort(CustomList<T> entityList);

}
