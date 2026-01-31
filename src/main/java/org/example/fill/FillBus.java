package org.example.fill;

import org.example.domain.Bus;
import org.example.infrastructure.CustomList;

public interface FillBus {
    public CustomList<Bus> fill();
}
