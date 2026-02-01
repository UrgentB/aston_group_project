package org.example.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.example.domain.Bus;
import org.junit.jupiter.api.Test;

public class CustomListTests {
    
    @Test
    public void testPositiveCustomListCreateNoParams() {
        CustomList<Bus> list = new CustomList<Bus>();
        assertTrue(list.size() == 0);
    }

    @Test
    public void testPositiveCustomListCreateFromNegativeCapacity() {
        CustomList<Bus> list = new CustomList<Bus>(-1);
        assertTrue(list.size() == 0);
    }

    @Test
    public void testPositiveCustomListAdd() {
        CustomList<Bus> list = new CustomList<Bus>();
        Bus bus = new Bus();
        list.add(bus);
        assertTrue(list.size() == 1);
        assertEquals(bus, list.get(0));
    }

    @Test
    public void testPositiveCustomListIterate() {
        CustomList<Bus> list1 = new CustomList<Bus>();
        for (int i = 0; i < 10; i++) {
            Bus bus = new Bus();
            bus.setNumber((int) (Math.random() * 100));
            list1.add(bus);
        }

        Iterator<Bus> iter = list1.iterator();
        int i = 0;
        while (iter.hasNext()) {
            assertEquals(list1.get(i++), iter.next());
        }
    }

    @Test
    public void testPositiveCustomListToString() {
        CustomList<Integer> list = new CustomList<>();
        list.add(5);

        assertEquals("5", list.toString());
    }

    @Test
    public void testPositiveCustomListSet() {
        CustomList<Bus> list = new CustomList<>(1);
        Bus bus = new Bus();
        list.set(0, bus);

        assertEquals(bus, list.get(0));
    }
}
