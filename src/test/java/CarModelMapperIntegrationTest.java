import com.database.entity.CarModelEntity;
import com.domain.mapper.CarModelMapper;
import com.domain.model.CarModel;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;

public class CarModelMapperIntegrationTest {

    private CarModelMapper mapper
            = Mappers.getMapper(CarModelMapper.class);

    @Test
    public void givenCarModelToCarModelEntity_whenMaps_thenCorrect() {
        CarModel carModel = new CarModel();
        carModel.setModel("SourceModel");
        carModel.setBrand("sourceBrand");
        carModel.setQualityClass("A");
        CarModelEntity carModelEntity = mapper.carModelToCarModelEntity(carModel);

        assertEquals(carModel.getModel(), carModelEntity.getModel());
        assertEquals(carModel.getBrand(), carModelEntity.getBrand());
        assertEquals(carModel.getQualityClass(), carModelEntity.getQualityClass());
    }

    @Test
    public void givenCarModelEntityToCarModel_whenMaps_thenCorrect() {
        CarModelEntity carModelEntity = new CarModelEntity();
        carModelEntity.setModel("SourceModel");
        carModelEntity.setBrand("sourceBrand");
        carModelEntity.setQualityClass("A");
        CarModel carModel = mapper.carModelEntityToCarModel(carModelEntity);

        assertEquals(carModelEntity.getModel(), carModel.getModel());
        assertEquals(carModelEntity.getBrand(), carModel.getBrand());
        assertEquals(carModelEntity.getQualityClass(), carModel.getQualityClass());
    }

}
