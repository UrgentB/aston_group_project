package org.example.application;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

import java.io.IOException;

public class BusImportService implements ImportService<Bus> {

    private InputStrategy<Bus> inputStrategy;

    //Использует валидатор для преобразования текстового файла в список объектов типа Bus и возвращает этот список
    @Override
    public CustomList<Bus> load() throws IOException {
        return inputStrategy.loadData();
    }

    @Override
    public void setStrategy(InputType inputType) {
        inputStrategy = inputType.getStrategy();
    }
}
