package org.example;

import org.example.Model.Bus;
import org.example.Read_From_File.ReadFromFile;
import org.example.Validator.BusValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BusImportService {
    private final ReadFromFile readFromFile;
    private final BusValidator busValidator;

    public BusImportService(ReadFromFile readFromFile, BusValidator busParcer){
        this.readFromFile = readFromFile;
        this.busValidator = busParcer;
    }

    //Использует валидатор для преобразования текстового файла в список объектов типа Bus и возвращает этот список
    public List<Bus> compile(String path) throws IOException {
        List<String> lines = readFromFile.read(path);
        List<Bus> busList = new ArrayList<>();
        for(String line : lines){
            busList.add(busValidator.parcer(line));
        }
        return busList;
    }
}
