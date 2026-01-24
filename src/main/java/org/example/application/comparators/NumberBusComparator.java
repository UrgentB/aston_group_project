package org.example.application.comparators;

import java.util.Comparator;

import org.example.domain.Bus;

public class NumberBusComparator implements Comparator<Bus> {

    @Override
    public int compare(Bus bus1, Bus bus2) {
        return bus1.getNumber().compareTo(bus2.getNumber());
    }
    
}
