package com.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

public class Booking implements Serializable {

    private int id;
    private User user;
    private Car car;
    private int bookingStatusCode = 1;

    private long numberOfDays;
    private Date pickUpDate;
    private Date returnDate;
    private BigDecimal totalPrice;

    private boolean driver = false;
    private String cancelComment = "";

    public Booking() {

    }


    public Booking(User user, Car car, String inputDateOfRent, String inputDateOfReturn) {
        this.user = user;
        this.car = car;
        this.pickUpDate = Date.valueOf(inputDateOfRent);
        this.returnDate = Date.valueOf(inputDateOfReturn);
        calculateNumberOfDays();
        calculateTotalPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getBookingStatusCode() {
        return bookingStatusCode;
    }

    public void setBookingStatusCode(int bookingStatusCode) {
        this.bookingStatusCode = bookingStatusCode;
    }

    public long getNumberOfDays() {
        calculateNumberOfDays();
        return numberOfDays;
    }

    public Date getDateOfRent() {
        return pickUpDate;
    }

    public void setDateOfRent(Date dateOfRent) {
        this.pickUpDate = dateOfRent;
        calculateNumberOfDays();

    }

    public void calculateNumberOfDays() {
        long dateBeforeInMs = pickUpDate.getTime();
        long dateAfterInMs = returnDate.getTime();
        long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);
        numberOfDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public Date getDateOfReturn() {
        return returnDate;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.returnDate = dateOfReturn;
        calculateNumberOfDays();
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public String getCancelComment() {
        return cancelComment;
    }

    public void setCancelComment(String cancelComment) {
        this.cancelComment = cancelComment;
    }

    public BigDecimal getTotalPrice() {
        calculateTotalPrice();
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice() {
        calculateNumberOfDays();
        totalPrice = car.getRentPricePerDay().multiply(BigDecimal.valueOf(numberOfDays));
        if (driver) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(100));
        }
    }

    @Override
    public String toString() {
        return "Booking: " +
                "user_login='" + user.getLogin() + '\'' +
                ", car_brand='" + car.getCarModel().getBrand() + '\'' +
                ", pickup date='" + getPickUpDate() + '\'' +
                ", return date='" + getReturnDate() + '\'' +
                ", number of days ='" + getNumberOfDays() + '\'' +
                ", driver ='" + isDriver() + '\'' +
                ", total_price ='" + getTotalPrice();
    }
}
