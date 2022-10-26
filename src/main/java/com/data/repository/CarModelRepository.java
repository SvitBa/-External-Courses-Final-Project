package com.data.repository;

import com.data.DBException;
import com.data.DataBaseConnection;
import com.data.entity.CarModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarModelRepository {

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

    public static void createCarModel(CarModel carModel) {
        try ( Connection connection = DataBaseConnection.getInstance().getConnection();
              PreparedStatement statement = connection.prepareStatement(CREATE_CAR_MODEL)
        ) {
            statement.setString(1, carModel.getBrand());
            statement.setString(2, carModel.getModel());
            statement.setString(3, carModel.getQualityClass());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<CarModel> getAllCarModel() throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL_CAR_MODEL)
        ) {
            while (resultSet.next()) {
                CarModel model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }

        } catch (SQLException e) {
            throw new DBException("FIND All carModel FAIL", e);
        }
        return carModelList;
    }

    public static List<CarModel> getCarModelByQuality(String qualityClass) throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_MODEL_BY_QUALITY_CLASS)
        ) {
            statement.setString(1, qualityClass);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarModel model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }
        } catch (SQLException e) {
            throw new DBException("FIND carModel by quality FAIL", e);
        }
        return carModelList;
    }

    public static List<CarModel> getCarModelByBrand(String brand) throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_MODEL_BY_BRAND)
        ) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarModel model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }

        } catch (SQLException e) {
            throw new DBException("FIND carModel by brand FAIL", e);
        }
        return carModelList;
    }

    public static List<CarModel> getCarModelByQualityAndBrand(String qualityClass, String brand) throws DBException {
        List<CarModel> carModelList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(FIND_ALL_CAR_MODEL_QUALITY_CLASS_AND_BRAND)
        ) {
            statement.setString(1, qualityClass);
            statement.setString(2, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarModel model = extractCarModelFromResultSet(resultSet);
                carModelList.add(model);
            }
        } catch (SQLException e) {
            throw new DBException("FIND carModel by quality and brand FAIL", e);

        }
        return carModelList;
    }


    public static CarModel getCarModelId(int id) {

        CarModel carModel = null;
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_CAR_MODEL_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carModel = extractCarModelFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carModel;
    }


    public static CarModel updateCarModel(CarModel carModel) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
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
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_CAR_MODEL)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    public static List<String> getBrandList() throws DBException {
        List<String> brandList = new ArrayList<>();
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_BRAND_LIST)
        ) {
            while (rs.next()) {
                String newBrand = rs.getString("brand");
                brandList.add(newBrand);
            }

        } catch (SQLException e) {
            throw new DBException("FIND brand list FAIL", e);
        }
        return brandList;
    }

    public static List<String> getQualityList() throws DBException {
        List<String> qualityList = new ArrayList<>();
        try (java.sql.Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_QUALITY_LIST)
        ) {
            while (rs.next()) {
                String newBrand = rs.getString("quality_class");
                qualityList.add(newBrand);
            }
        } catch (SQLException e) {
            throw new DBException("FIND quality list FAIL", e);
        }
        return qualityList;
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
