package org.example.application;

import java.io.IOException;

import org.example.infrastructure.CustomList;

public interface ImportService<T> {
    
    CustomList<T> load() throws IOException;

    CustomList<T> streamLoad() throws IOException;
}
