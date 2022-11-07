package com.web.mapper;


import com.domain.model.CarModel;
import com.web.model.CarModelDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarModelMapperWeb {

    CarModelDTO carModelToCarModelDTO(CarModel carModel);

    CarModel carModelDTOToCarModel(CarModelDTO carModelDTO);
}
