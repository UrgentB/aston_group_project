package org.example.application;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

import java.io.IOException;

public class BusImportService {

    private InputStrategy<Bus> inputStrategy;

    //Использует валидатор для преобразования текстового файла в список объектов типа Bus и возвращает этот список
    public CustomList<Bus> compile(String path) throws IOException {
        return inputStrategy.loadData();
    }

    public void setStrategy(InputType inputType) {
        inputStrategy = inputType.getStrategy();
    }
}
