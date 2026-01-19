package org.example.application;

import org.example.infrastructure.CustomList;

/**
 * Generic strategy interface for loading data into a CustomList.
 * @param <T> the type of elements in the CustomList
 */

public interface InputStrategy<T> {

    /**
     * Loads data into a CustomList.
     * Randomly: generates data.
     * Manually: prompts user for input. Requires InputController или еще что-то с доступом к консоли.
     * From File: reads data from a file. Использовать FileStorage.
     * @return the loaded data
     */

    CustomList<T> loadData();

}
