package com.domain.mapper;


import com.database.entity.CarEntity;
import com.domain.model.Car;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {

    CarEntity carToCarEntity(Car car);

    Car carEntityToCar(CarEntity carEntity);
}
