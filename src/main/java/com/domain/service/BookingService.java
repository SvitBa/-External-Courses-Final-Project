package com.domain.service;

import com.database.entity.BookingEntity;
import com.database.repository.BookingDAO;
import com.database.repository.BookingRepository;
import com.domain.mapper.BookingMapper;
import com.domain.model.Booking;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class BookingService implements Service<Booking, Integer> {

    private final BookingDAO bookingRepository;
    private final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

    public BookingService() {
        this.bookingRepository = new BookingRepository();
    }

    @Override
    public void create(Booking booking) {
        BookingEntity bookingEntity = mapper.bookingToBookingEntity(booking);
        bookingRepository.createBooking(bookingEntity);

    }

    @Override
    public Booking read(Integer id) {
        return mapper.bookingEntityToBooking(bookingRepository.getBookingById(id));
    }

    @Override
    public List<Booking> readAll() {
        return bookingRepository.getAllBooking().stream()
                .map(mapper::bookingEntityToBooking)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.deleteBookingById(booking.getId());

    }

    @Override
    public void update(Booking booking) {
        BookingEntity bookingEntity = mapper.bookingToBookingEntity(booking);
        bookingRepository.updateBookingDetails(bookingEntity);
    }


    public Booking updateState(Booking booking) {
        BookingEntity bookingEntity = mapper.bookingToBookingEntity(booking);
        BookingEntity updatedBooking = bookingRepository.updateBookingStatusCode(bookingEntity);
        return mapper.bookingEntityToBooking(updatedBooking);
    }

    public List<Booking> getAllBookingByUserId(int userId) {
        return bookingRepository.getAllBookingByUserId(userId).stream()
                .map(mapper::bookingEntityToBooking)
                .collect(Collectors.toList());
    }

}
