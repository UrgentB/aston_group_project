package org.example.application.sorting;

public interface SortingPredicate<T, I> {
    boolean execute(T element, I index);
}
