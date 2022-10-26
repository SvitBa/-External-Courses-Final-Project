package com.data.repository;

import com.data.DBException;
import com.data.DataBaseConnection;
import com.data.entity.Car;
import com.data.entity.CarModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {

    private static final String CREATE_CAR = "INSERT INTO car (car_model_id, price_per_day) VALUES (?, ?)";

    private static final String FIND_ALL_CAR = "SELECT * FROM car LEFT JOIN car_model ON car_model_id = car_model.id";

    private static final String FIND_ALL_CAR_BY_QUALITY_CLASS = FIND_ALL_CAR + " WHERE quality_class = ?";
    private static final String FIND_ALL_CAR_BY_BRAND = FIND_ALL_CAR + " WHERE brand = ?";
    private static final String FIND_ALL_CAR_QUALITY_CLASS_AND_BRAND = FIND_ALL_CAR +
            " WHERE quality_class = ? AND brand = ?";
    private static final String FIND_CAR_BY_ID = FIND_ALL_CAR + " WHERE car_id = ?";

    private static final String UPDATE_CAR = "UPDATE car SET quality_class = ?, brand =?, model = ? WHERE id = ?";

    private static final String DELETE_CAR = "DELETE FROM car WHERE id = ?";


    public static List<Car> getAllCar() throws DBException {
        List<Car> carList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_CAR)
        ) {
            while (rs.next()) {
                Car newCar = extractCarFromResultSet(rs);
                carList.add(newCar);
            }
        } catch (SQLException e) {
            throw new DBException("FIND all car FAIL", e);
        }
        return carList;
    }

    public static List<Car> getCarByQualityAndBrand(String qualityClass, String brand) throws DBException {
        List<Car> carList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_QUALITY_CLASS_AND_BRAND)
        ) {
            statement.setString(1, qualityClass);
            statement.setString(2, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car newCar = extractCarFromResultSet(resultSet);
                carList.add(newCar);
            }
        } catch (SQLException e) {
            throw new DBException("FIND all car by quality and brand FAIL", e);
        }
        return carList;
    }

    public static List<Car> getCarByQuality(String qualityClass)throws DBException {
        List<Car> carList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_BY_QUALITY_CLASS)
        ) {
            statement.setString(1, qualityClass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car newCar = extractCarFromResultSet(resultSet);
                carList.add(newCar);
            }
        } catch (SQLException e) {
            throw new DBException("FIND all car by quality FAIL", e);
        }
        return carList;
    }

    public static List<Car> getCarByBrand(String brand)throws DBException {
        List<Car> carList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_BY_BRAND)
        ) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car newCar = extractCarFromResultSet(resultSet);
                carList.add(newCar);
            }
        } catch (SQLException e) {
            throw new DBException("FIND all car by brand FAIL", e);
        }
        return carList;
    }

    public static void createCar(Car car) {
        try ( Connection connection = DataBaseConnection.getInstance().getConnection();
              PreparedStatement statement = connection.prepareStatement(CREATE_CAR);
                ) {
            statement.setInt(1, Integer.parseInt(car.getModelId()));
            statement.setBigDecimal(2, car.getRentPricePerDay());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Car getCarById(int id) {
        Car car = null;
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_CAR_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = extractCarFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public static Car updateCar(Car car) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_CAR)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public static void deleteCarModelById(int id) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_CAR)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt("car_id"));
        car.setRentPricePerDay(resultSet.getBigDecimal("price_per_day"));
        CarModel model = CarModelRepository.getCarModelId(resultSet.getInt("car_model_id"));
        car.setCarModel(model);
        car.setCarCurrentAvailable(resultSet.getBoolean("currently_available"));
        return car;
    }

}
