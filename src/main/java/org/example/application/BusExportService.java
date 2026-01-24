package org.example.application; 

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

public class BusExportService {

    String path;
    Boolean fileWritingRegime;

    public BusExportService(Boolean fileWritingRegime, String path) {
        this.fileWritingRegime = fileWritingRegime;
        this.path = path;
    }
   
    public void save(CustomList<Bus> data){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, fileWritingRegime))) {
            for(Bus bus : data) {
                bw.write(bus.toString() + '\n');
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    }
}