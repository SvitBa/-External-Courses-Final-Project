package com.domain.service;

import com.database.entity.CarEntity;
import com.database.repository.CarDAO;
import com.database.repository.CarRepository;
import com.domain.mapper.CarMapper;
import com.domain.model.Car;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;


public class CarService implements Service<Car, Integer> , FindBrandAndQualityList<Car> {
    private final CarDAO carRepository;
    private final CarMapper mapper = Mappers.getMapper(CarMapper.class);

    public CarService() {
        this.carRepository = new CarRepository();
    }

    @Override
    public void create(Car car) {
        CarEntity carEntity = mapper.carToCarEntity(car);
        carRepository.createCar(carEntity);
    }

    @Override
    public Car read(Integer id) {
        return mapper.carEntityToCar(carRepository.getCarById(id));
    }

    @Override
    public List<Car> readAll() {
        return carRepository.getAllCar().stream()
                .map(mapper::carEntityToCar)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Car car) {
        CarEntity carEntity = mapper.carToCarEntity(car);
        carRepository.updateCar(carEntity);
    }

    @Override
    public void delete(Car car) {
        carRepository.deleteCarById(car.getId());
    }

    @Override
    public List<Car> findByQualityAndBrand(String qualityClass, String brand) {
        return carRepository.getCarByQualityAndBrand(qualityClass, brand).stream()
                .map(mapper::carEntityToCar)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> findByQuality(String qualityClass) {
        return carRepository.getCarByQuality(qualityClass).stream()
                .map(mapper::carEntityToCar)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> findByBrand(String brand) {
        return carRepository.getCarByBrand(brand).stream()
                .map(mapper::carEntityToCar)
                .collect(Collectors.toList());
    }

    @Override
    public Car updateCarAvailable(Car car) {
        CarEntity carEntity = mapper.carToCarEntity(car);
        CarEntity updatedCar = carRepository.updateCarCurrentAvailable(carEntity);
        return mapper.carEntityToCar(updatedCar);
    }

    @Override
    @Deprecated
    public List<String> getBrandList() {
        return null;
    }

    @Override
    @Deprecated
    public List<String> getQualityList() {
        return null;
    }
}
