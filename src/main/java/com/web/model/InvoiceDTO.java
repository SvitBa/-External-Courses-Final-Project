package com.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDTO {

    private int id;
    private String type;
    private String status;
    private BookingDTO booking;
    private BigDecimal totalPrice;
}
