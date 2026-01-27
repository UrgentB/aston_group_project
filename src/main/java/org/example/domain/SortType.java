package org.example.domain;

/**
 * Enumeration representing keys for different types of sorting methods.
 */
public enum SortType {
    /**
     * Sorting by number.
     */
    SORT_NUMBER("Сортировать по номеру."),
    /**
     * Sorting by model.
     */
    SORT_MODEL("Сортировать по модели."),
    /**
     * Sorting by mileage.
     */
    SORT_MILEAGE("Сортировать по пробегу."),
    /**
     * Sort all fields (basic)
     */
    SORT_BASIC("Сортировать по всем полям.");

    private final String title;

    SortType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
