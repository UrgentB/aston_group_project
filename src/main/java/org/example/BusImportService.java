package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.Read_From_File.ReadFromTxtFile;
import org.example.Validator.BusValidator;
import org.example.domain.Bus;

public class BusImportService {
    private final ReadFromTxtFile readFromTxtFile;
    private final BusValidator busValidator;

    public BusImportService(ReadFromTxtFile readFromTxtFile, BusValidator busParcer){
        this.readFromTxtFile = readFromTxtFile;
        this.busValidator = busParcer;
    }

    //Использует валидатор для преобразования текстового файла в список объектов типа Bus и возвращает этот список
    public List<Bus> compile(String path) throws IOException {
        List<String> lines = readFromTxtFile.read(path);
        List<Bus> busList = new ArrayList<>();
        for(String line : lines){
            busList.add(busValidator.parcer(line));
        }
        return busList;
    }
}
