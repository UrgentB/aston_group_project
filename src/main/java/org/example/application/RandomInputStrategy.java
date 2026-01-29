package org.example.application;

import java.io.IOException;

import org.example.domain.Bus;
import org.example.fill.FillBusRandom;
import org.example.infrastructure.CustomList;

public class RandomInputStrategy implements InputStrategy<Bus> {

    private final FillBusRandom filler = new FillBusRandom();

    @Override
    public CustomList<Bus> loadData() throws IOException {
        return filler.fill();
    }
    
}
