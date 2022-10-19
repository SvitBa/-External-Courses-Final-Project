package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Car implements Serializable {

    private int id;

    private BigDecimal rentPricePerDay;

    private String modelId;

    private CarModel carModel;

    private boolean carCurrentAvailable;

    public Car( BigDecimal rentPricePerDay, String modelId) {
        this.id = 1;
        this.rentPricePerDay = rentPricePerDay;
        this.modelId = modelId;
        this.carCurrentAvailable = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setRentPricePerDay(BigDecimal rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String model) {
        this.modelId = model;
    }

    public void setCarCurrentAvailable(boolean carCurrentAvailable) {
        this.carCurrentAvailable = carCurrentAvailable;
    }
}
