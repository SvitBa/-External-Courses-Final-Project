package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking implements Serializable {

    private int id;
    private User user;
    private Car car;
    private int bookingStatusCode;

    private long numberOfDays;
    private LocalDateTime pickUpDate;
    private LocalDateTime returnDate;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
    private BigDecimal totalPrice;


    public Booking(User user, Car car, String inputDateOfRent, String inputDateOfReturn) {
        this.user = user;
        this.car = car;
        this.pickUpDate = LocalDateTime.from(LocalDate.parse(inputDateOfRent, dtf));
        this.returnDate = LocalDateTime.from(LocalDate.parse(inputDateOfReturn, dtf));
        calculateNumberOfDays();
        calculateTotalPrice();
    }

    public void calculateNumberOfDays() {
        numberOfDays= Duration.between(returnDate,pickUpDate).toDays();
    }

    public void calculateTotalPrice() {
        car.getRentPricePerDay().multiply(BigDecimal.valueOf(numberOfDays));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getBookingStatusCode() {
        return bookingStatusCode;
    }

    public void setBookingStatusCode(int bookingStatusCode) {
        this.bookingStatusCode = bookingStatusCode;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public LocalDateTime getDateOfRent() {
        return pickUpDate;
    }

    public void setDateOfRent(LocalDateTime dateOfRent) {
        this.pickUpDate = dateOfRent;
        calculateNumberOfDays();

    }

    public LocalDateTime getDateOfReturn() {
        return returnDate;
    }

    public void setDateOfReturn(LocalDateTime dateOfReturn) {
        this.returnDate = dateOfReturn;
        calculateNumberOfDays();
    }


    public BigDecimal getTotalPrice() {
        calculateTotalPrice ();
        return totalPrice;
    }
}
