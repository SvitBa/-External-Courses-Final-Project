package com.database.repository;

import com.database.DataBaseConnection;
import com.database.entity.CarModelEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarModelRepository implements CarModelDAO{

    static final Logger logger = Logger.getLogger(CarModelRepository.class);

    private static final String CREATE_CAR_MODEL = "INSERT INTO car_model (brand, model, quality_class) VALUES (?, ?, ?)";

    private static final String FIND_BRAND_LIST = "SELECT DISTINCT brand FROM car_model order by brand asc";
    private static final String FIND_QUALITY_LIST = "SELECT DISTINCT quality_class FROM car_model order by quality_class asc";

    private static final String FIND_ALL_CAR_MODEL = "SELECT * FROM car_model";
    private static final String FIND_ALL_CAR_MODEL_BY_QUALITY_CLASS = FIND_ALL_CAR_MODEL + " WHERE quality_class = ?";
    private static final String FIND_ALL_CAR_MODEL_BY_BRAND = FIND_ALL_CAR_MODEL + " WHERE brand = ?";
    private static final String FIND_ALL_CAR_MODEL_QUALITY_CLASS_AND_BRAND = FIND_ALL_CAR_MODEL +
            " WHERE quality_class = ? AND brand = ?";
    private static final String FIND_CAR_MODEL_BY_ID = FIND_ALL_CAR_MODEL + " WHERE id = ?";

    private static final String UPDATE_CAR_MODEL = "UPDATE car_model SET quality_class = ?, brand =?, model = ? WHERE id = ?";

    private static final String DELETE_CAR_MODEL = "DELETE FROM car_model WHERE id = ?";

   @Override public void createCarModel(CarModelEntity carModel) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CAR_MODEL)
        ) {
            statement.setString(1, carModel.getBrand());
            statement.setString(2, carModel.getModel());
            statement.setString(3, carModel.getQualityClass());
            statement.execute();
            logger.info("CarModelRepository created carModel");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error while adding carModel to database");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
    }

    @Override  public List<CarModelEntity> getAllCarModel() {
        List<CarModelEntity> carModelList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL_CAR_MODEL)
        ) {
            while (resultSet.next()) {
                CarModelEntity model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }
            logger.info("CarModelRepository received all carModel");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error while receiving all carModels");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return carModelList;
    }

    @Override public List<CarModelEntity> getCarModelByQuality(String qualityClass) {
        List<CarModelEntity> carModelList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CAR_MODEL_BY_QUALITY_CLASS)
        ) {
            statement.setString(1, qualityClass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarModelEntity model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }
            logger.info("CarModelRepository received all carModels by quality");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error  while receiving carModels by quality");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return carModelList;
    }

    @Override public List<CarModelEntity> getCarModelByBrand(String brand) {
        List<CarModelEntity> carModelList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CAR_MODEL_BY_BRAND)
        ) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarModelEntity model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }
            logger.info("CarModelRepository received all carModel by brand");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error  while receiving carModels by brand");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return carModelList;
    }

    @Override public List<CarModelEntity> getCarModelByQualityAndBrand(String qualityClass, String brand) {
        List<CarModelEntity> carModelList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CAR_MODEL_QUALITY_CLASS_AND_BRAND)
        ) {
            statement.setString(1, qualityClass);
            statement.setString(2, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarModelEntity model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }
            logger.info("CarModelRepository received all carModel by quality and brand");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error  while receiving carModels by quality and brand");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return carModelList;
    }


    @Override public CarModelEntity getCarModelId(int id) {
        CarModelEntity carModel = null;
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_CAR_MODEL_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carModel = extractCarModelFromResultSet(resultSet);
            }
            logger.info("CarModelRepository received carModel by id");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error while receiving carModel by id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return carModel;
    }


    @Override public void updateCarModel(CarModelEntity carModel) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_MODEL)
        ) {
            statement.setString(1, carModel.getQualityClass());
            statement.setString(2, carModel.getBrand());
            statement.setString(3, carModel.getModel());
            statement.setInt(4, carModel.getId());
            statement.executeUpdate();
            logger.info("CarModelRepository updated carModel");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error while updating carModel");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
    }


    @Override public void deleteCarModelById(int id) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_CAR_MODEL)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("CarModelRepository deleted carModel");
        } catch (SQLException throwable) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error during delete CarModel by id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
    }


    @Override  public List<String> getBrandList() {
        List<String> brandList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_BRAND_LIST)
        ) {
            while (rs.next()) {
                String newBrand = rs.getString("brand");
                brandList.add(newBrand);
            }
            logger.info("CarModelRepository received all Brands");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error while finding brand list");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return brandList;
    }

    @Override public List<String> getQualityList() {
        List<String> qualityList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_QUALITY_LIST)
        ) {
            while (rs.next()) {
                String newBrand = rs.getString("quality_class");
                qualityList.add(newBrand);
            }
            logger.info("CarModelRepository received Quality list");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarModelRepository got error while receiving quality list");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarModelRepository closed connection");
        }
        return qualityList;
    }

    private CarModelEntity extractCarModelFromResultSet(ResultSet resultSet) throws SQLException {
        CarModelEntity carModel = new CarModelEntity();
        carModel.setId(resultSet.getInt("id"));
        carModel.setQualityClass(resultSet.getString("quality_class"));
        carModel.setBrand(resultSet.getString("brand"));
        carModel.setModel(resultSet.getString("model"));
        return carModel;
    }
}
