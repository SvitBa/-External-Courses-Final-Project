package com.database.repository;

import com.database.entity.CarEntity;

import java.util.List;

public interface CarDAO {

    List<CarEntity> getAllCar();

    List<CarEntity> getCarByQualityAndBrand(String qualityClass, String brand);

    List<CarEntity> getCarByQuality(String qualityClass);

    List<CarEntity> getCarByBrand(String brand);

    void createCar(CarEntity car);

    CarEntity getCarById(int id);

    CarEntity updateCar(CarEntity car);

    CarEntity updateCarCurrentAvailable(CarEntity car);

    void deleteCarModelById(int id);
}
