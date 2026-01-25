package org.example.application;

import org.example.application.sorting.SortStrategy;
import org.example.infrastructure.CustomList;

/**
 * Abstract service class for processing data.
 * Сервис-слой для обработки данных (поиск и сортировка).
 * @param <T> the type of elements being processed
 */
public abstract class Service<T> {
    /**
     * Strategy for sorting data.
     */
    protected SortStrategy<T> sortStrategy;

    /**
     * Processes data using the defined sort strategy.
     * @param data the data to be processed
     * @return the processed data
     */
    public abstract CustomList<T> processData(CustomList<T> data);

    public void setSortStrategy(SortStrategy<T> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
}
