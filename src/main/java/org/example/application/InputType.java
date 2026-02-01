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
    INPUT_RANDOM {
        public InputStrategy<Bus> getStrategy() {
            return new RandomBusInputStrategyAdapter();
        }
    },
    /**
     * Input provided manually by the user.
     */
    INPUT_MANUAL {
        public InputStrategy<Bus> getStrategy() {
            return new ManualBusInputStrategyAdapter();
        }
    },
    /**
     * Input read from a file.
     */
    INPUT_FROM_FILE {
        public InputStrategy<Bus> getStrategy() {
            return new FileBusInputStrategyAdapter();
        }
    };

    public abstract InputStrategy<Bus> getStrategy();
}
