package com.domain.mapper;


import com.database.entity.BookingEntity;
import com.domain.model.Booking;
import org.mapstruct.Mapper;

@Mapper
public interface BookingMapper {

    BookingEntity bookingToBookingEntity(Booking booking);

    Booking bookingEntityToBooking(BookingEntity bookingEntity);
}
