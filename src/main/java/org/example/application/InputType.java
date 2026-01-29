package org.example.application;

import org.example.domain.Bus;

/**
 * Enumeration representing keys for different types of input methods.
 * Ключи для стратегий ввода.
 */
public enum InputType {
    /**
     * Input generated randomly.
     */
    INPUT_RANDOM(new RandomInputStrategy()),
    /**
     * Input provided manually by the user.
     */
    INPUT_MANUAL(new RandomInputStrategy()),
    /**
     * Input read from a file.
     */
    INPUT_FROM_FILE(new RandomInputStrategy());

    private final InputStrategy<Bus> inputStrategy;

    private InputType(InputStrategy<Bus> inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    public InputStrategy<Bus> getStrategy() {
        return inputStrategy;
    }
}
