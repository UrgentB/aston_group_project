package org.example.application;

import org.example.domain.Bus;
import org.example.Validator.BusValidator;
import org.example.infrastructure.CustomCollector;
import org.example.infrastructure.CustomList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class BusImportService implements ImportService<Bus>{
    private Readable readFromFile;
    private BusValidator busValidator;
    private String path;

    private static BusImportService busImportService = new BusImportService();

    private BusImportService() {}

    public static BusImportService getInstance() {
        return busImportService;
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

    @Override
    public CustomList<Bus> streamLoad(){
        Iterable<Bus> buses;
        try(Stream<String> stream = Files.lines(Paths.get(path))) {
            buses = stream.map(busValidator::parcer)
                    .collect(CustomCollector.toCustomList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (CustomList<Bus>) buses;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setReadable(Readable readable) {
        this.readFromFile = readable;
    }

    public void setValidator(BusValidator validator) {
        this.busValidator = validator;
    }

    public void reset() {
        this.readFromFile = null;
        this.busValidator = null;
        this.path = null;
    }
}
