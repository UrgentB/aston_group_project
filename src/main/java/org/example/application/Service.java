package org.example.application;

import org.example.application.sorting.SortAlgorythm;
import org.example.application.sorting.SortType;
import org.example.infrastructure.CustomList;

/**
 * Generic service interface for processing CustomList data.
 * @param <T> the type of elements in the CustomList
 */
public interface Service<T> {

    /**
     * Sorts the given CustomList data using the specified sort type and algorithm.
     * @param data
     * @param sortType
     * @param sortAlgorythm
     * @return
     */
    CustomList<T> sort(CustomList<T> data, SortType sortType, SortAlgorythm sortAlgorythm);

    /**
     * Reads data using the specified input type.
     * @param inputType
     * @return
     */
    CustomList<T> read(InputType inputType);

    /**
     * Writes the given CustomList data to the output.
     * @param data
     */
    void write(CustomList<T> data);
}
