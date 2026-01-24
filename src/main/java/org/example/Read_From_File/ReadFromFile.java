package org.example.Read_From_File;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {
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
