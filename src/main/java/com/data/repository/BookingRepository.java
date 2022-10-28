package com.data.repository;

import com.data.DBException;
import com.data.DataBaseConnection;
import com.data.entity.Booking;
import com.data.entity.Car;
import com.data.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private static final String CREATE_BOOKING = "INSERT INTO booking (user_id, car_id, pickup_date, return_date, " +
            "booking_status_code_id, driver, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL_BOOKING = "SELECT * FROM booking";

    private static final String FIND_BOOKING_BY_ID = FIND_ALL_BOOKING + " WHERE id = ?";

    private static final String FIND_BOOKING_BY_USER_ID = FIND_ALL_BOOKING + " WHERE user_id = ?";

    private static final String UPDATE_BOOKING_DETAILS = "UPDATE booking SET pickup_date =?, " +
            "return_date = ?, driver = ? WHERE id = ?";

    private static final String UPDATE_BOOKING_STATUS = "UPDATE booking SET booking_status_code_id = ? , cancel_comment = ? WHERE id = ?";

    private static final String DELETE_BOOKING = "DELETE FROM booking WHERE id = ?";

    public static void createBooking(Booking newBooking) {
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_BOOKING)
        ) {
            statement.setInt(1, newBooking.getUser().getId());
            statement.setInt(2, newBooking.getCar().getId());
            statement.setDate(3, newBooking.getPickUpDate());
            statement.setDate(4, newBooking.getReturnDate());
            statement.setInt(5, newBooking.getBookingStatusCode());
            statement.setBoolean(6, newBooking.isDriver());
            statement.setBigDecimal(7, newBooking.getTotalPrice());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Booking> getAllBooking() throws DBException {
        List<Booking> bookingList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL_BOOKING)
        ) {
            while (resultSet.next()) {
                Booking newBooking = extractBookingFromResultSet(resultSet);
                bookingList.add(newBooking);
            }
        } catch (SQLException e) {
            throw new DBException("FIND All booking FAIL", e);
        }
        return bookingList;
    }

    public static List<Booking> getAllBookingByUserId(int userId) throws DBException {
        List<Booking> bookingList = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKING_BY_USER_ID)
        ) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Booking newBooking = extractBookingFromResultSet(resultSet);
                bookingList.add(newBooking);
            }
        } catch (SQLException e) {
            throw new DBException("FIND All booking FAIL", e);
        }
        return bookingList;
    }

    public static Booking getBookingById(int id) throws DBException {
        Booking booking = null;
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKING_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                booking = extractBookingFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public static Booking updateBookingDetails(Booking newBooking) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_BOOKING_DETAILS)
        ) {
            statement.setDate(1, newBooking.getPickUpDate());
            statement.setDate(2, newBooking.getReturnDate());
            statement.setBoolean(3, newBooking.isDriver());
            statement.setInt(4, newBooking.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBooking;
    }

    public static Booking updateBookingStatusCode(Booking newBooking) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_BOOKING_STATUS)
        ) {
            statement.setInt(1, newBooking.getBookingStatusCode());
            statement.setString(2, newBooking.getCancelComment());
            statement.setInt(3, newBooking.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBooking;
    }

    public static void deleteBookingById(int id) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BOOKING)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static Booking extractBookingFromResultSet(ResultSet resultSet) throws SQLException, DBException {
        Booking booking = new Booking();
        booking.setId(resultSet.getInt("id"));
        User user = UserRepository.getUserById(resultSet.getInt("user_id"));
        booking.setUser(user);
        Car car = CarRepository.getCarById(resultSet.getInt("car_id"));
        booking.setCar(car);
        booking.setPickUpDate(resultSet.getDate("pickup_date"));
        booking.setReturnDate(resultSet.getDate("return_date"));
        booking.setDriver(resultSet.getBoolean("driver"));
        booking.setCancelComment(resultSet.getString("cancel_comment"));
        booking.setTotalPrice(resultSet.getBigDecimal("total_price"));
        return booking;
    }
}
