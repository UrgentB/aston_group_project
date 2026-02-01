package org.example.application; 

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

public class BusExportService implements ExportService<Bus>{

    String path;
    Boolean fileWritingRegime;

    public BusExportService(Boolean fileWritingRegime) {
        this.fileWritingRegime = fileWritingRegime;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void save(CustomList<Bus> data){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, fileWritingRegime))){
            data.forEach(bus -> {
                try {
                    bw.write(bus.toString() + '\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    }
}