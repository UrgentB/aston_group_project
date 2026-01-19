package org.example.domain;

/**
 * Domain class representing a Bus entity.
 * Реализовать Builder паттерн.
 */

public class Bus {
    
    private String number;
    private String model;
    private Long mileage;

    public Bus(String number, String model, Long mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

    public Bus() {}

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getMileage() {
        return this.mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }
}
