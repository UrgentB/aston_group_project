package org.example.Read_From_File;

import org.example.application.Readable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromTxtFile implements Readable {
    public List<String> read(String path) throws IOException{
        String line;
        List<String> rows = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))){
            while((line = reader.readLine()) != null){
                rows.add(line);
            }
        }
        return rows;
    }
}
