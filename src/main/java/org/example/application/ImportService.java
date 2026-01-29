package org.example.application;

import java.io.IOException;

import org.example.infrastructure.CustomList;

public interface ImportService<T> {
    
    public CustomList<T> load() throws IOException;

    public void setStrategy(InputType inputType);
}
