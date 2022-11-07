package com.domain.service;

import java.util.List;

public interface FindBrandAndQualityList<Entity>{

    List<Entity> findByQualityAndBrand(String qualityClass, String brand);

    List<Entity> findByQuality(String qualityClass);

    List<Entity> findByBrand(String brand);

    Entity updateCarAvailable(Entity Entity);

    List<String> getBrandList();

    List<String> getQualityList();
}
