package com.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Car {

    private int id;
    private BigDecimal rentPricePerDay;
    private String modelId;
    private CarModel carModel;
    private boolean carCurrentAvailable;
}
