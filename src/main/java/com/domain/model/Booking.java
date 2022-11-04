package com.domain.model;


import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Booking {

    private int id;
    private User user;
    private Car car;
    private int bookingStatusCode = 1;
    private long numberOfDays;
    private Date pickUpDate;
    private Date returnDate;
    private BigDecimal totalPrice;
}
