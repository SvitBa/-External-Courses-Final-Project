package com.database.repository;

import com.database.DataBaseConnection;
import com.database.entity.CarEntity;
import com.database.entity.CarModelEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository implements CarDAO {


    static final Logger logger = Logger.getLogger(CarRepository.class);
    private CarModelDAO carModelRepository = new CarModelRepository();

    private static final String CREATE_CAR = "INSERT INTO car (car_model_id, price_per_day) VALUES (?, ?)";

    private static final String FIND_ALL_CAR = "SELECT * FROM car LEFT JOIN car_model ON car_model_id = car_model.id";

    private static final String FIND_ALL_CAR_BY_QUALITY_CLASS = FIND_ALL_CAR + " WHERE quality_class = ?";
    private static final String FIND_ALL_CAR_BY_BRAND = FIND_ALL_CAR + " WHERE brand = ?";
    private static final String FIND_ALL_CAR_QUALITY_CLASS_AND_BRAND = FIND_ALL_CAR +
            " WHERE quality_class = ? AND brand = ?";
    private static final String FIND_CAR_BY_ID = FIND_ALL_CAR + " WHERE car_id = ?";

    private static final String UPDATE_CAR = "UPDATE car SET quality_class = ?, brand =?, model = ? WHERE car_id = ?";

    private static final String UPDATE_CAR_CURRENT_AVAILABLE = "UPDATE car SET currently_available = ? WHERE car_id = ?";

    private static final String DELETE_CAR = "DELETE FROM car WHERE id = ?";


   @Override public List<CarEntity> getAllCar() {
        List<CarEntity> carList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_CAR)
        ) {
            while (rs.next()) {
                CarEntity newCar = extractCarFromResultSet(rs);
                carList.add(newCar);
            }
            logger.info("CarRepository find all Cars");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while getting all car");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return carList;
    }

    @Override public List<CarEntity> getCarByQualityAndBrand(String qualityClass, String brand) {
        List<CarEntity> carList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CAR_QUALITY_CLASS_AND_BRAND)
        ) {
            statement.setString(1, qualityClass);
            statement.setString(2, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarEntity newCar = extractCarFromResultSet(resultSet);
                carList.add(newCar);
            }
            logger.info("CarRepository find all Cars by quality and brand");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while getting Cars by quality and brand");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return carList;
    }

    @Override public List<CarEntity> getCarByQuality(String qualityClass) {
        List<CarEntity> carList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CAR_BY_QUALITY_CLASS)
        ) {
            statement.setString(1, qualityClass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarEntity newCar = extractCarFromResultSet(resultSet);
                carList.add(newCar);
            }
            logger.info("CarRepository find all car by quality");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while getting car by quality");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return carList;
    }

    @Override public List<CarEntity> getCarByBrand(String brand) {
        List<CarEntity> carList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CAR_BY_BRAND)
        ) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarEntity newCar = extractCarFromResultSet(resultSet);
                carList.add(newCar);
            }
            logger.info("CarRepository find all car by brand");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while getting all car by brand");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return carList;
    }

    @Override public void createCar(CarEntity car) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CAR);
        ) {
            statement.setInt(1, Integer.parseInt(car.getModelId()));
            statement.setBigDecimal(2, car.getRentPricePerDay());
            statement.execute();
            logger.info("CarRepository created car");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while creating car");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
    }

    @Override public CarEntity getCarById(int id) {
        CarEntity car = null;
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_CAR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = extractCarFromResultSet(resultSet);
            }
            logger.info("CarRepository got car by id");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while getting car by id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return car;
    }

    @Override public CarEntity updateCar(CarEntity car) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CAR)
        ) {
            statement.executeUpdate();
            logger.info("CarRepository update car");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while updating car");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return car;
    }

    @Override public CarEntity updateCarCurrentAvailable(CarEntity car) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_CURRENT_AVAILABLE)
        ) {
            statement.setBoolean(1, car.isCarCurrentAvailable());
            statement.setInt(2, car.getId());
            statement.executeUpdate();
            logger.info("CarRepository update car availability");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while updating car availability");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
        return car;
    }

    @Override public void deleteCarById(int id) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_CAR)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("CarRepository delete car");
        } catch (SQLException throwable) {
            DataBaseConnection.rollback(connection);
            logger.error("CarRepository got error while deleting car");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("CarRepository closed connection");
        }
    }

    private CarEntity extractCarFromResultSet(ResultSet resultSet) throws SQLException {
        CarEntity car = new CarEntity();
        car.setId(resultSet.getInt("car_id"));
        car.setRentPricePerDay(resultSet.getBigDecimal("price_per_day"));
        CarModelEntity model = carModelRepository.getCarModelId(resultSet.getInt("car_model_id"));
        car.setCarModel(model);
        car.setCarCurrentAvailable(resultSet.getBoolean("currently_available"));
        return car;
    }

}
