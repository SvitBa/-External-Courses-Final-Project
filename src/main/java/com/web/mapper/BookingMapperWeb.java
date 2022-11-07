package com.web.mapper;


import com.domain.model.Booking;
import com.web.model.BookingDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BookingMapperWeb {

    BookingDTO bookingToBookingDTO(Booking booking);

    Booking bookingDTOToBooking(BookingDTO bookingDTO);
}
