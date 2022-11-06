package com.web.model;


import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class BookingDTO {

    private int id;
    private UserDTO user;
    private CarDTO car;
    private int bookingStatusCode = 1;
    private long numberOfDays;
    private Date pickUpDate;
    private Date returnDate;
    private BigDecimal totalPrice;
}
