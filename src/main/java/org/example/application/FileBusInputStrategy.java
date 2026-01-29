package org.example.application;

import java.io.IOException;

import org.example.Read_From_File.ReadFromFile;
import org.example.Validator.BusValidator;
import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

public class FileBusInputStrategy implements InputStrategy<Bus> {

    private final ReadFromFile readFromFile = new ReadFromFile();
    private final BusValidator busValidator = new BusValidator();
    private String path = "/";

    @Override
    public CustomList<Bus> loadData() throws IOException {
        CustomList<String> lines = readFromFile.read(path);
        CustomList<Bus> busList = new CustomList<>();
        for(String line : lines){
            busList.add(busValidator.parcer(line));
        }
        return busList;
    }
    
}
