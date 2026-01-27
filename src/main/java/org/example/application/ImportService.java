package org.example.application;

import org.example.infrastructure.CustomList;

public interface ImportService<T> {
    
    public CustomList<T> load();
}
