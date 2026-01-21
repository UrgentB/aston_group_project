package org.example.domain.comparators;

import java.util.Comparator;

import org.example.domain.Bus;

public class MileageBusComparator implements Comparator<Bus> {

    @Override
    public int compare(Bus bus1, Bus bus2) {
        return bus1.getMileage().compareTo(bus2.getMileage());
    }
    
}
