package com.web.mapper;


import com.domain.model.Car;
import com.web.model.CarDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapperWeb {

    CarDTO carToCarDTO(Car car);

    Car carDTOToCar(CarDTO carDTO);
}
