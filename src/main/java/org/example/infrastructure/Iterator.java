package org.example.infrastructure;

public interface Iterator<T> {
    boolean hasNext();

    T next();
}