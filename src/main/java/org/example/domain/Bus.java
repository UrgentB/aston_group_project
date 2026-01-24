package org.example.domain;

import java.util.Comparator;

/**
 * Domain class representing a Bus entity.
 * Реализован Builder паттерн.
 */
public class Bus implements Comparator<Bus> {

public class Bus {
    
    private Integer number;
    private String model;
    private Double mileage;

    public Bus(Integer number, String model, Double mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

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
        this.number = number;
    }  
  
    public String getModel() {
        return model;
    }
  
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public double getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return String.format("Bus{number=%d, model='%s', mileage=%.1f}",
                number, model, mileage);
    }

    @Override
    public int compare(Bus b1, Bus b2) {
        return Integer.compare(b1.number, b2.number);
    }

    public static Comparator<Bus> byNumber() {
        return Comparator.comparingInt(Bus::getNumber);
    }

    public static Comparator<Bus> byModel() {
        return Comparator.comparing(Bus::getModel);
    }

    public static Comparator<Bus> byMileage() {
        return Comparator.comparingDouble(Bus::getMileage);
    }

    /**
     * Комбинированная сортировка
     */
    public static Comparator<Bus> fullComparator() {
        return Comparator.comparingInt(Bus::getNumber)
                .thenComparing(Bus::getModel)
                .thenComparingDouble(Bus::getMileage);
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