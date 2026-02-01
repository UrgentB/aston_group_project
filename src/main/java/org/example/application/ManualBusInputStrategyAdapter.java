package org.example.application;

import java.io.IOException;

import org.example.domain.Bus;
import org.example.fill.FillBus;
import org.example.fill.FillBusManually;
import org.example.fill.StreamFillBusManually;
import org.example.infrastructure.CustomList;

public class ManualBusInputStrategyAdapter implements InputStrategy<Bus> {

    private final FillBus streamFiller = new StreamFillBusManually();
    private final FillBus filler = new FillBusManually();

    @Override
    public CustomList<Bus> loadData() throws IOException {
        return filler.fill();
    }

    @Override
    public CustomList<Bus> loadStreamData() throws IOException {
        return streamFiller.fill();
    }
    
}
