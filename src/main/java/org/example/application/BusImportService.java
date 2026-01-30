package org.example.application;

import org.example.domain.Bus;
import org.example.Validator.BusValidator;
import org.example.infrastructure.CustomList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusImportService implements ImportService<Bus>{
    private final Readable readFromFile;
    private final BusValidator busValidator;
    private String path;

    public BusImportService(Readable readFromFile, BusValidator busParcer, String path){
        this.readFromFile = readFromFile;
        this.busValidator = busParcer;
        this.path = path;
    }

    //Использует валидатор для преобразования текстового файла в список объектов типа Bus и возвращает этот список
    @Override
    public CustomList<Bus> load() {
        List<String> lines = null;
        CustomList<Bus> busList = new CustomList<>();
        try {
            lines = readFromFile.read(path);
            for(String line : lines){
                busList.add(busValidator.parcer(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return busList;
    }

    public CustomList<Bus> streamLoad(){
        Iterable<Bus> buses;
        try(Stream<String> stream = Files.lines(Paths.get(path))) {
            buses = stream.map(busValidator::parcer)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (CustomList<Bus>) buses;
    }
}
