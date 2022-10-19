package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Invoice implements Serializable {
    private int id;
    private String type;
    private String status;
    private Booking booking;
    private BigDecimal totalPrice;

}
