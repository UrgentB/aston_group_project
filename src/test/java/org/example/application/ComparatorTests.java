package org.example.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.application.comparators.MileageBusComparator;
import org.example.application.comparators.ModelBusComparator;
import org.example.application.comparators.NumberBusComparator;
import org.example.domain.Bus;
import org.junit.jupiter.api.Test;

public class ComparatorTests {
    
    @Test
    public void testPositiveModelBusComparator() {
        Bus bus1 = new Bus();
        bus1.setModel("jndsfsdjn");
        Bus bus2 = new Bus();
        bus2.setModel("pdsfllsdlfsd");
        assertEquals(new ModelBusComparator().compare(bus1, bus2), bus1.getModel().compareTo(bus2.getModel()));
    }

    @Test
    public void testPositiveNumberBusComparator() {
        Bus bus1 = new Bus();
        bus1.setNumber(1);
        Bus bus2 = new Bus();
        bus2.setNumber(2);
        assertEquals(new NumberBusComparator().compare(bus1, bus2), bus1.getNumber().compareTo(bus2.getNumber()));
    }

    @Test
    public void testPositiveMileageBusComparator() {
        Bus bus1 = new Bus();
        bus1.setMileage(1.1);
        Bus bus2 = new Bus();
        bus2.setMileage(2.2);
        assertEquals(new MileageBusComparator().compare(bus1, bus2), bus1.getMileage().compareTo(bus2.getMileage()));
    }
}
