package org.example.domain.comparators;

import java.util.Comparator;

import org.example.domain.Bus;

public class ModelBusComparator implements Comparator<Bus> {

    @Override
    public int compare(Bus bus1, Bus bus2) {
        return bus1.getModel().compareTo(bus2.getModel());
    }
    
}
