package org.example.application;

import java.io.IOException;

import org.example.domain.Bus;
import org.example.fill.FillBusManually;
import org.example.infrastructure.CustomList;

public class ManualInputStrategy implements InputStrategy<Bus> {

    private final FillBusManually filler = new FillBusManually();

    @Override
    public CustomList<Bus> loadData() throws IOException {
        return filler.fill();
    }
    
}
