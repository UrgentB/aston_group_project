package org.example.application.sorting;

import org.example.infrastructure.CustomList;

/**
 * Generic strategy abstraction for sorting a CustomList.
 * @param <T> the type of elements in the CustomList
 */

public interface SortStrategy<T> {

    /**
     * Sorts the given CustomList.
     * Поле, которое нужно отсортировать, определяется компаратором.
     * @param entityList
     */

    public abstract void sort(CustomList<T> entityList);

}
