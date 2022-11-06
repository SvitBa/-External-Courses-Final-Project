package com.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarDTO {

    private int id;
    private BigDecimal rentPricePerDay;
    private String modelId;
    private CarModelDTO carModel;
    private boolean carCurrentAvailable;
}
