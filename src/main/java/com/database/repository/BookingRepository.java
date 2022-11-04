package com.database.repository;

import com.database.DataBaseConnection;
import com.database.entity.BookingEntity;
import com.database.entity.CarEntity;
import com.database.entity.UserEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements BookingDAO{

    static final Logger logger = Logger.getLogger(BookingRepository.class);

    private UserDAO userRepository = new UserRepository();
    private CarDAO carRepository = new CarRepository();

    private static final String CREATE_BOOKING = "INSERT INTO booking (user_id, car_id, pickup_date, return_date, " +
            "booking_status_code_id, driver, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL_BOOKING = "SELECT * FROM booking";

    private static final String FIND_BOOKING_BY_ID = FIND_ALL_BOOKING + " WHERE id = ?";

    private static final String FIND_BOOKING_BY_USER_ID = FIND_ALL_BOOKING + " WHERE user_id = ?";

    private static final String UPDATE_BOOKING_DETAILS = "UPDATE booking SET pickup_date =?, " +
            "return_date = ?, driver = ? WHERE id = ?";

    private static final String UPDATE_BOOKING_STATUS = "UPDATE booking SET booking_status_code_id = ? , cancel_comment = ? WHERE id = ?";

    private static final String DELETE_BOOKING = "DELETE FROM booking WHERE id = ?";

    @Override public void createBooking(BookingEntity newBooking) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_BOOKING)
        ) {
            statement.setInt(1, newBooking.getUser().getId());
            statement.setInt(2, newBooking.getCar().getId());
            statement.setDate(3, newBooking.getPickUpDate());
            statement.setDate(4, newBooking.getReturnDate());
            statement.setInt(5, newBooking.getBookingStatusCode());
            statement.setBoolean(6, newBooking.isDriver());
            statement.setBigDecimal(7, newBooking.getTotalPrice());
            statement.execute();
            logger.info("BookingRepository created booking");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while creating the booking");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
    }

    @Override public List<BookingEntity> getAllBooking()  {
        List<BookingEntity> bookingList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL_BOOKING)
        ) {
            while (resultSet.next()) {
                BookingEntity newBooking = extractBookingFromResultSet(resultSet);
                bookingList.add(newBooking);
            }
            logger.info("BookingRepository find all bookings");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while getting all the booking");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
        return bookingList;
    }

    @Override public List<BookingEntity> getAllBookingByUserId(int userId) {
        List<BookingEntity> bookingList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOKING_BY_USER_ID)
        ) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookingEntity newBooking = extractBookingFromResultSet(resultSet);
                bookingList.add(newBooking);
            }
            logger.info("BookingRepository find bookings by user id");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while getting all user bookings");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
        return bookingList;
    }

    @Override public BookingEntity getBookingById(int id) {
        BookingEntity booking = null;
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BOOKING_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                booking = extractBookingFromResultSet(resultSet);
                logger.info("BookingRepository find booking by id");
            }
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while getting the booking by id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
        return booking;
    }

    @Override public BookingEntity updateBookingDetails(BookingEntity newBooking) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKING_DETAILS)
        ) {
            statement.setDate(1, newBooking.getPickUpDate());
            statement.setDate(2, newBooking.getReturnDate());
            statement.setBoolean(3, newBooking.isDriver());
            statement.setInt(4, newBooking.getId());
            statement.executeUpdate();
            logger.info("BookingRepository updated booking details");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while updating the booking details");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
        return newBooking;
    }

    @Override public BookingEntity updateBookingStatusCode(BookingEntity newBooking) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKING_STATUS)
        ) {
            statement.setInt(1, newBooking.getBookingStatusCode());
            statement.setString(2, newBooking.getCancelComment());
            statement.setInt(3, newBooking.getId());
            statement.executeUpdate();
            logger.info("BookingRepository updated booking status code");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while updating the booking status code");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
        return newBooking;
    }

    @Override public void deleteBookingById(int id) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_BOOKING)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("BookingRepository deleted booking by id");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("BookingRepository got error while deleting the booking");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("BookingRepository closed connection");
        }
    }

    private BookingEntity extractBookingFromResultSet(ResultSet resultSet) throws SQLException {
        BookingEntity booking = new BookingEntity();
        booking.setId(resultSet.getInt("id"));
        UserEntity user = userRepository.getUserById(resultSet.getInt("user_id"));
        booking.setUser(user);
        CarEntity car = carRepository.getCarById(resultSet.getInt("car_id"));
        booking.setCar(car);
        booking.setPickUpDate(resultSet.getDate("pickup_date"));
        booking.setReturnDate(resultSet.getDate("return_date"));
        booking.setDriver(resultSet.getBoolean("driver"));
        booking.setCancelComment(resultSet.getString("cancel_comment"));
        booking.setTotalPrice(resultSet.getBigDecimal("total_price"));
        return booking;
    }
}
