package org.example;

import java.util.Comparator;

public class Bus implements Comparator<Bus> {
    private final int id;  // временный идентификатор (пока нет реальных полей)

    private Bus(Builder builder) {
        this.id = builder.id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Bus #" + id;
    }

    @Override
    public int compare(Bus b1, Bus b2) {
        return Integer.compare(b1.id, b2.id);
    }

    public static class Builder {
        private int id;

        public Builder id(int id) {
            if (id <= 0) throw new IllegalArgumentException("ID должен быть > 0");
            this.id = id;
            return this;
        }

        public Bus build() {
            return new Bus(this);
        }
    }
}