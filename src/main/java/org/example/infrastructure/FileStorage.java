package org.example.infrastructure;

/**
 * Generic interface for file storage operations.
 * @param <T> the type of elements to be stored
 */

public interface FileStorage<T> {

    /**
     * Saves the given data to storage.
     * @param data the data to be saved
     */
    void save(CustomList<T> data);

    /**
     * Loads data from storage.
     * @return the loaded data
     */
    CustomList<T> load();
}
