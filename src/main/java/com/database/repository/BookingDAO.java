package com.database.repository;

import com.database.entity.BookingEntity;

import java.util.List;

public interface BookingDAO {

    void createBooking(BookingEntity newBooking);

    List<BookingEntity> getAllBooking();

    List<BookingEntity> getAllBookingByUserId(int userId);

    BookingEntity getBookingById(int id);

    BookingEntity updateBookingDetails(BookingEntity booking);

    BookingEntity updateBookingStatusCode(BookingEntity booking);

    void deleteBookingById(int id);
}
