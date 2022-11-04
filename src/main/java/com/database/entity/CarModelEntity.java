package com.database.entity;

import java.io.Serializable;

public class CarModelEntity implements Serializable {

    private int id;

    private String brand;
    private String model;
    private String qualityClass;

    public CarModelEntity() {

    }


    public CarModelEntity(String brand, String model, String qualityClass) {
        this.id = 1;
        this.brand = brand;
        this.model = model;
        this.qualityClass = qualityClass;
    }

    public CarModelEntity(int id, String brand, String model, String qualityClass) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.qualityClass = qualityClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQualityClass() {
        return qualityClass;
    }

    public void setQualityClass(String qualityClass) {
        this.qualityClass = qualityClass;
    }

    @Override
    public String toString() {
        return "CarModel: " +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", qualityClass='" + qualityClass ;
    }
}
