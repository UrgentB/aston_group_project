package org.example.application;

import org.example.domain.Bus;

public class BusInputStrategyFactory {
    
    public static InputStrategy<Bus> getStrategy(InputType inputType) {
        return inputType.getStrategy();
    }
}
