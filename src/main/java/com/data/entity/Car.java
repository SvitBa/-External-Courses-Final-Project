package com.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Car implements Serializable {

    private int id;

    private BigDecimal rentPricePerDay;

    private String modelId;

    private CarModel carModel;

    private boolean carCurrentAvailable;

    public Car() {

    }

    public Car(BigDecimal rentPricePerDay, String modelId) {
        this.id = 1;
        this.rentPricePerDay = rentPricePerDay;
        this.modelId = modelId;
        this.carCurrentAvailable = true;
    }

    public Car(int carId, BigDecimal rentPricePerDay, CarModel model, boolean carCurrentAvailable) {
        this.id = carId;
        this.rentPricePerDay = rentPricePerDay;
        this.carModel = model;
        this.carCurrentAvailable = carCurrentAvailable;
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

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public boolean isCarCurrentAvailable() {
        return carCurrentAvailable;
    }

    public void setCarCurrentAvailable(boolean carCurrentAvailable) {
        this.carCurrentAvailable = carCurrentAvailable;
    }
}
