package org.example.domain;

import java.util.Objects; 
/**
 * Domain class representing a Bus entity.
 * Реализован Builder паттерн.
 */
public class Bus implements Comparable<Bus> {
    
    private Integer number;
    private String model;
    private Double mileage;

    public Bus(Integer number, String model, Double mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

    public Bus() {}

    private Bus(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public void setModel(String model) {
        this.model = model;
    }  
  
    public String getModel() {
        return model;
    }
  
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getMileage() {
        return mileage;
    }

     // ← НОВЫЕ МЕТОДЫ ДОБАВЛЕНЫ ЗДЕСЬ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(number, bus.number) &&
               Objects.equals(model, bus.model) &&
               Objects.equals(mileage, bus.mileage);
    }
    @Override
    public int hashCode() {
        return Objects.hash(number, model, mileage);
    }

    @Override
    public int compareTo(Bus other) {
        // Сравнение для сортировки
        int result = Objects.compare(this.number, other.number, Integer::compare);
        if (result != 0) return result;
        result = Objects.compare(this.model, other.model, String::compareTo);
        if (result != 0) return result;
        return Objects.compare(this.mileage, other.mileage, Double::compare);
    }
     // ← НОВЫЕ МЕТОДЫ ДОБАВЛЕНЫ ЗДЕСЬ
    
    @Override
    public String toString() {
        return String.format("Bus{number=%d, model='%s', mileage=%.1f}",
                number, model, mileage);
    }

    /**
     * Базовая сортировка по всем 3 полям.
     * Сравнивает последовательно номер, модель и пробег автобуса.
     * @param other
     * @return
     */

    @Override
    public int compareTo(Bus other) {
        int result =  this.number.compareTo(other.number);
        if (result != 0) return result;
        result = this.model.compareTo(other.model);
        if (result != 0) return result;
        return this.mileage.compareTo(other.mileage);
    }

    public static Bus createInstance(int number, String model, double mileage) {
        return new Builder()
                .number(number)
                .model(model)
                .mileage(mileage)
                .build();
    }
    public static class Builder {
        private int number;
        private String model;
        private double mileage;

        public Builder number(int number) {
            if (number <= 0) {
                throw new IllegalArgumentException("Number must be positive");
            }
            this.number = number;
            return this;
        }

        public Builder model(String model) {
            if (model == null || model.trim().isEmpty()) {
                throw new IllegalArgumentException("Model cannot be empty");
            }
            this.model = model.trim();
            return this;
        }

        public Builder mileage(double mileage) {
            if (mileage < 0) {
                throw new IllegalArgumentException("Mileage cannot be negative");
            }
            this.mileage = mileage;
            return this;
        }

        public Bus build() {
            return new Bus(this);
        }
    }
}
