package com.database.repository;

import com.database.entity.CarModelEntity;

import java.util.List;

public interface CarModelDAO {

    void createCarModel(CarModelEntity carModel);

    List<CarModelEntity> getAllCarModel();

    List<CarModelEntity> getCarModelByQuality(String qualityClass);

    List<CarModelEntity> getCarModelByBrand(String brand);

    List<CarModelEntity> getCarModelByQualityAndBrand(String qualityClass, String brand);

    CarModelEntity getCarModelId(int id);

    void updateCarModel(CarModelEntity carModel);

    void deleteCarModelById(int id);

    List<String> getBrandList();

    List<String> getQualityList();
}
