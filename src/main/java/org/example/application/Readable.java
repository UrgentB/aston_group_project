package org.example.application;

import java.io.IOException;
import java.util.List;

public interface Readable {
    List<String> read(String path) throws IOException;
}
