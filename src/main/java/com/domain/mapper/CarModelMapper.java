package com.domain.mapper;


import com.database.entity.CarModelEntity;
import com.domain.model.CarModel;
import org.mapstruct.Mapper;

@Mapper
public interface CarModelMapper {

    CarModelEntity carModelToCarModelEntity(CarModel carModel);

    CarModel carModelEntityToCarModel(CarModelEntity carModelEntity);
}
