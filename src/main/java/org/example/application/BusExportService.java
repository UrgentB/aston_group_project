package org.example.application; 

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;
import org.example.infrastructure.FileStorage;

class BusExportService implements FileStorage<Bus>{

    String path;
    Boolean fileWritingRegime;

    public BusExportService(Boolean fileWritingRegime, String path) {
        this.fileWritingRegime = fileWritingRegime;
        this.path = path;
    }
   
    @Override
    public void save(CustomList<Bus> data){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, fileWritingRegime))){
            data.forEach(bus -> {
                bw.write(bus.toString() + '\n');
            });
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    }

    @Override
    public CustomList<Bus> load(){
         throw new UnsupportedOperationException("Method 'myMethod' not implemented yet.");
    }
}