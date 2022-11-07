package com.domain.service;

import com.database.entity.CarModelEntity;
import com.database.repository.CarModelDAO;
import com.database.repository.CarModelRepository;
import com.domain.mapper.CarModelMapper;
import com.domain.model.CarModel;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class CarModelService implements Service<CarModel, Integer>, FindBrandAndQualityList<CarModel> {
    private final CarModelDAO carModelRepository;
    private final CarModelMapper mapper = Mappers.getMapper(CarModelMapper.class);

    public CarModelService() {
        this.carModelRepository = new CarModelRepository();
    }

    @Override
    public void create(CarModel carModel) {
        CarModelEntity carModelEntityEntity = mapper.carModelToCarModelEntity(carModel);
        carModelRepository.createCarModel(carModelEntityEntity);
    }

    @Override
    public CarModel read(Integer id) {
        return mapper.carModelEntityToCarModel(carModelRepository.getCarModelId(id));
    }

    @Override
    public List<CarModel> readAll() {
        return carModelRepository.getAllCarModel().stream()
                .map(mapper::carModelEntityToCarModel)
                .collect(Collectors.toList());
    }

    @Override
    public void update(CarModel carModel) {
        CarModelEntity carModelEntity = mapper.carModelToCarModelEntity(carModel);
        carModelRepository.updateCarModel(carModelEntity);
    }

    @Override
    public void delete(CarModel carModel) {
        carModelRepository.deleteCarModelById(carModel.getId());
    }

    @Override
    public List<CarModel> findByQualityAndBrand(String qualityClass, String brand) {
        return carModelRepository.getCarModelByQualityAndBrand(qualityClass, brand).stream()
                .map(mapper::carModelEntityToCarModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarModel> findByQuality(String qualityClass) {
        return carModelRepository.getCarModelByQuality(qualityClass).stream()
                .map(mapper::carModelEntityToCarModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarModel> findByBrand(String brand) {
        return carModelRepository.getCarModelByBrand(brand).stream()
                .map(mapper::carModelEntityToCarModel)
                .collect(Collectors.toList());
    }

    @Override
    @Deprecated
    public CarModel updateCarAvailable(CarModel car) {
        return null;
    }

    @Override
    public List<String> getBrandList() {
        return carModelRepository.getBrandList();
    }

    @Override
    public List<String> getQualityList() {
        return carModelRepository.getQualityList();
    }
}
