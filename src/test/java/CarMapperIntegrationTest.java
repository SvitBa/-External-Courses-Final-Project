import com.database.entity.CarEntity;
import com.database.entity.CarModelEntity;
import com.domain.mapper.CarMapper;
import com.domain.model.Car;
import com.domain.model.CarModel;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CarMapperIntegrationTest {

    private CarMapper mapper
            = Mappers.getMapper(CarMapper.class);

    @Test
    public void givenCarToCarEntity_whenMaps_thenCorrect() {
        Car car = new Car();
        car.setId(1);
        car.setModelId("1");
        car.setRentPricePerDay(BigDecimal.valueOf(500));
        car.setCarCurrentAvailable(true);

        CarModel carModel = new CarModel();
        carModel.setModel("SourceModel");
        carModel.setBrand("sourceBrand");
        carModel.setQualityClass("A");
        car.setCarModel(carModel);

        CarEntity carEntity = mapper.carToCarEntity(car);

        assertEquals(car.getId(), carEntity.getId());
        assertEquals(car.getModelId(), carEntity.getModelId());
        assertEquals(car.getRentPricePerDay(), carEntity.getRentPricePerDay());
        assertEquals(car.isCarCurrentAvailable(), carEntity.isCarCurrentAvailable());


        assertEquals(car.getCarModel().getModel(), carEntity.getCarModel().getModel());
        assertEquals(car.getCarModel().getBrand(), carEntity.getCarModel().getBrand());
        assertEquals(car.getCarModel().getQualityClass(), carEntity.getCarModel().getQualityClass());
    }

    @Test
    public void givenCarEntityToCar_whenMaps_thenCorrect() {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(1);
        carEntity.setModelId("1");
        carEntity.setRentPricePerDay(BigDecimal.valueOf(500));
        carEntity.setCarCurrentAvailable(true);

        CarModelEntity carModel = new CarModelEntity();
        carModel.setModel("SourceModel");
        carModel.setBrand("sourceBrand");
        carModel.setQualityClass("A");
        carEntity.setCarModel(carModel);

        Car car = mapper.carEntityToCar(carEntity);

        assertEquals(carEntity.getId(), car.getId());
        assertEquals(carEntity.getModelId(), car.getModelId());
        assertEquals(carEntity.getRentPricePerDay(), car.getRentPricePerDay());
        assertEquals(carEntity.isCarCurrentAvailable(), car.isCarCurrentAvailable());

        assertEquals(carEntity.getCarModel().getModel(), car.getCarModel().getModel());
        assertEquals(carEntity.getCarModel().getBrand(), car.getCarModel().getBrand());
        assertEquals(carEntity.getCarModel().getQualityClass(), car.getCarModel().getQualityClass());

    }

}
