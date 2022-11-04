package com.domain.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Invoice {

    private int id;
    private String type;
    private String status;
    private Booking booking;
    private BigDecimal totalPrice;
}
