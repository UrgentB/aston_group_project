package org.example.domain;

/**
 * Domain class representing a Bus entity.
 * Реализовать Builder паттерн.
 */

public class Bus {
    
    private Integer number;
    private String model;
    private Double mileage;

    public Bus(Integer number, String model, Double mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

    public Bus() {}

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getMileage() {
        return this.mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
}
