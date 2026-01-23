package org.example.application;

import org.example.infrastructure.CustomList;

public interface ExportService<T> {

    public void save(CustomList<T> data);

}
