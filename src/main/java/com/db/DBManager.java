package com.db;

import com.entity.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DBManager {
	//INSERTS
	private static final String INSERT_USER = "INSERT INTO user (login, email, password, passport ) VALUES (?, ?, ?, ?)";
	private static final String INSERT_CAR = "INSERT INTO car (car_model_id, price_per_day) VALUES (?, ?)";
	private static final String INSERT_BOOKING_FOR_USER = "INSERT INTO booking (user_id, car_id, pickup_date, return_date) VALUES (?, ?, ?, ?)";


	//FIND
	private static final String FIND_ALL_USER = "SELECT * FROM user";
	private static final String FIND_ALL_CAR = "SELECT quality_class, brand, model, price_per_day FROM car" +
			"LEFT JOIN car_model ON car_model_id = car_model.id ";
	private static final String FIND_ALL_CAR_BY_QUALITY_CLASS = "SELECT quality_class, brand, model, price_per_day FROM car " +
			"LEFT JOIN car_model ON car_model_id = car_model.id WHERE quality_class = ?";
	private static final String FIND_ALL_CAR_BY_BRAND = "SELECT quality_class, brand, model, price_per_day FROM car " +
			"LEFT JOIN car_model ON car_model_id = car_model.id WHERE brand = ?";
	private static final String FIND_ALL_CAR_BY_QUALITY_CLASS_AND_BRAND = "SELECT quality_class, brand, model, price_per_day FROM car " +
			"LEFT JOIN car_model ON car_model_id = car_model.id WHERE quality_class = ? AND brand = ?";

	private static final String FIND_ALL_AVAILABLE_CAR = "SELECT * FROM rental_car WHERE car_available = true";
	private static final String FIND_ALL_BOOKING_BY_USER = "SELECT * FROM booking WHERE user_id = ?";

	private static final String GET_USER = "SELECT * FROM users WHERE login = ?";

	//DELETE
	private static final String DELETE_CAR = "DELETE FROM car WHERE id = ?";
	private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

	//UPDATE
	private static final String UPDATE_CAR = "UPDATE car SET price_per_day = ? WHERE id = ?";
	private static String CONNECTION_URL = "";
	private static DBManager instance = null;

	private DBManager() {

	}

	public static synchronized DBManager getInstance() {
		if (instance == null)
			instance = new DBManager();
		try {
			readPropertiesFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return instance;
	}

	private static void readPropertiesFile() throws IOException {
		Properties props = new Properties();
		FileInputStream in = new FileInputStream("app.properties");
		props.load(in);
		in.close();
		CONNECTION_URL = props.getProperty("connection.url");
	}

	public boolean insertCar(Car car) throws DBException {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL);
			 PreparedStatement statement = connection.prepareStatement(INSERT_CAR, Statement.RETURN_GENERATED_KEYS);
		) {
			statement.setString(1, car.getModelId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No rows affected");
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					car.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			throw new DBException("INSERT rent car FAIL", throwables);
		}

		return true;
	}



	public boolean deleteCar(Car car) throws DBException {
		if (car == null) return false;
		try (Connection con = DriverManager.getConnection(CONNECTION_URL);
			 PreparedStatement stmt = con.prepareStatement(DELETE_CAR);
		) {
			stmt.setInt(1, car.getId());
			stmt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean updateCar(Car car) throws DBException {
		try (Connection con = DriverManager.getConnection(CONNECTION_URL);
			 PreparedStatement statement = con.prepareStatement(UPDATE_CAR);
		) {
			statement.setString(1, car.getModelId());
			statement.setInt(2, car.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}



}
