package org.example.domain;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BusTest {

    //Тест успешного создания через Builder с реальными данными
    @Test
    void builder_shouldCreateValidBus() {
        Bus bus = new Bus.Builder()
                .number(111)
                .model("ЛиАЗ")
                .mileage(659292.0)
                .build();

        assertEquals(111, bus.getNumber());
        assertEquals("ЛиАЗ", bus.getModel());
        assertEquals(659292.0, bus.getMileage(), 0.001);
    }

    //Тест метода createInstance
    @Test
    void createInstance_shouldCreateValidBus() {
        Bus bus = Bus.createInstance(51, "ПАЗ", 120512.0);

        assertEquals(51, bus.getNumber());
        assertEquals("ПАЗ", bus.getModel());
        assertEquals(120512.0, bus.getMileage(), 0.001);
    }

    //Тесты на валидацию в Builder
    @Test
    void builder_shouldThrowWhenNumberInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bus.Builder().number(0).build();
        });
        assertEquals("Number must be positive", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bus.Builder().number(-5).build();
        });
        assertEquals("Number must be positive", exception.getMessage());
    }

    @Test
    void builder_shouldThrowWhenModelEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bus.Builder().model(null).build();
        });
        assertEquals("Model cannot be empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bus.Builder().model("   ").build();
        });
        assertEquals("Model cannot be empty", exception.getMessage());
    }

    @Test
    void builder_shouldThrowWhenMileageNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Bus.Builder().mileage(-100.0).build();
        });
        assertEquals("Mileage cannot be negative", exception.getMessage());
    }

    //Тест compare по номеру
    @Test
    void compare_shouldCompareByNumber() {
        Bus bus1 = Bus.createInstance(21, "Solaris", 461292.0);
        Bus bus2 = Bus.createInstance(43, "МАЗ", 256129.0);

        assertTrue(bus1.compare(bus1, bus2) < 0);  // 21 < 43
        assertTrue(bus1.compare(bus2, bus1) > 0);
        assertEquals(0, bus1.compare(bus1, bus1));
    }

    //Тест fullComparator с реальными данными
    @Test
    void fullComparator_shouldSortByAllFields() {
        Bus bus1 = Bus.createInstance(111, "ЛиАЗ", 659292.0);
        Bus bus2 = Bus.createInstance(111, "ЛиАЗ", 120512.0);  // тот же номер и модель, разный mileage
        Bus bus3 = Bus.createInstance(111, "ПАЗ", 659292.0);   // тот же номер, другая модель

        Comparator<Bus> comp = Bus.fullComparator();

        assertTrue(comp.compare(bus1, bus2) > 0);  // mileage 659292 > 120512
        assertTrue(comp.compare(bus2, bus3) < 0);  // model ЛиАЗ < ПАЗ
    }
}