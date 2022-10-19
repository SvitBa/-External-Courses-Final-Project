package com.db;

import com.entity.CarModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarModelDAOImpl implements CarModelDAO {

    private static final String CREATE_CAR_MODEL = "INSERT INTO car_model (brand, model, quality_class) VALUES (?, ?, ?)";

    private static final String FIND_ALL_CAR_MODEL = "SELECT * FROM car_model";
    private static final String FIND_ALL_CAR_MODEL_BY_QUALITY_CLASS = FIND_ALL_CAR_MODEL + " WHERE quality_class = ?";
    private static final String FIND_ALL_CAR_MODEL_BY_BRAND = FIND_ALL_CAR_MODEL + " WHERE brand = ?";
    private static final String FIND_ALL_CAR_MODEL_QUALITY_CLASS_AND_BRAND = FIND_ALL_CAR_MODEL +
            " WHERE quality_class = ? AND brand = ?";
    private static final String FIND_CAR_MODEL_BY_ID = FIND_ALL_CAR_MODEL + " WHERE id = ?";

    private static final String UPDATE_CAR_MODEL = "UPDATE car_model SET quality_class = ?, brand =?, model = ? WHERE id = ?";

    private static final String DELETE_CAR_MODEL = "DELETE FROM car_model WHERE id = ?";


    public static List<CarModel> getAllCarModel() throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DBConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL_CAR_MODEL)
        ) {
            while (rs.next()) {
                CarModel model = new CarModel(rs.getInt("id"), rs.getString("brand"),
                        rs.getString("model"), rs.getString("quality_class"));
//                model.setId(rs.getInt("id"));
                carModelList.add(model);
            }

        } catch (SQLException e) {
            throw new DBException("FIND carModel FAIL", e);
        }
        return carModelList;
    }

    public static List<CarModel> getCarModelByQuality(String qualityClass) throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_MODEL_BY_QUALITY_CLASS)
        ) {
            statement.setString(1, qualityClass);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CarModel model = new CarModel(rs.getString("brand"), rs.getString("model"),
                        rs.getString("quality_class"));
                carModelList.add(model);
            }
        } catch (SQLException e) {
            throw new DBException("FIND carModel by quality FAIL", e);
        }
        return carModelList;
    }

    public static List<CarModel> getCarModelByBrand(String brand) throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_MODEL_BY_BRAND)
        ) {
            statement.setString(1, brand);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CarModel model = new CarModel(rs.getString("brand"), rs.getString("model"),
                        rs.getString("quality_class"));
                carModelList.add(model);
            }

        } catch (SQLException e) {

            throw new DBException("FIND carModel by brand FAIL", e);

        }
        return carModelList;
    }

    public static List<CarModel> getCarModelByQualityAndBrand(String qualityClass, String brand) throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_MODEL_QUALITY_CLASS_AND_BRAND)
        ) {
            statement.setString(1, qualityClass);
            statement.setString(2, brand);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CarModel model = new CarModel(rs.getString("brand"), rs.getString("model"),
                        rs.getString("quality_class"));
                carModelList.add(model);
            }

        } catch (SQLException e) {

            throw new DBException("FIND carModel by quality and brand FAIL", e);

        }
        return carModelList;
    }


    public static void createCarModel(CarModel carModel) {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(CREATE_CAR_MODEL);


            statement.setString(1, carModel.getBrand());
            statement.setString(2, carModel.getModel());
            statement.setString(3, carModel.getQualityClass());
            statement.execute();

//            logger.info("CarModelDaoImpl created car_model");
        } catch (SQLException e) {
            DBConnection.rollback(connection);
//            logger.error("CarModelDaoImpl got error while creating the car_model");
        } finally {
            DBConnection.close(connection);
//            logger.info("CarModelDaoImpl closed connection");
        }
    }


    public static CarModel getCarModelId(int id) {

        Connection connection = DBConnection.getInstance().getConnection();
        CarModel carModel = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_CAR_MODEL_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carModel = extractCarModelFromResultSet(resultSet);
//                logger.info("CarModelDaoImpl read car_model");
            }
        } catch (SQLException e) {
            DBConnection.rollback(connection);
//            logger.error("CarModelDaoImpl got error while reading the car_model");
        } finally {
            DBConnection.close(connection);
//            logger.info("CarModelDaoImpl closed connection");
        }
        return carModel;
    }


    public static CarModel updateCarModel(CarModel carModel) {
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_CAR_MODEL)
        ) {
            statement.setString(1, carModel.getQualityClass());
            statement.setString(2, carModel.getBrand());
            statement.setString(3, carModel.getModel());
            statement.setInt(4, carModel.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }


    public static void deleteCarModelById(int id) {
        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_CAR_MODEL)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static CarModel extractCarModelFromResultSet(ResultSet resultSet) throws SQLException {
        CarModel carModel = new CarModel();
        carModel.setId(resultSet.getInt("id"));
        carModel.setQualityClass(resultSet.getString("quality_class"));
        carModel.setBrand(resultSet.getString("brand"));
        carModel.setModel(resultSet.getString("model"));
        return carModel;
    }
}
