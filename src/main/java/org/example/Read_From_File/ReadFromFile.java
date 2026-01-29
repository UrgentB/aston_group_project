package org.example.Read_From_File;

import java.io.*;

import org.example.infrastructure.CustomList;

public class ReadFromFile {
    public CustomList<String> read(String path) throws IOException{
        String line;
        CustomList<String> rows = new CustomList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))){
            while((line = reader.readLine()) != null){
                rows.add(line);
            }
        }
        return rows;
    }
}
