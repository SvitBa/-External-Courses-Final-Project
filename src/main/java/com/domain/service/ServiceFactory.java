package com.domain.service;

public class ServiceFactory {

    public BookingService getBookingService() {
        return new BookingService();
    }
    public CarModelService getCarModelService() {
        return new CarModelService();
    }
    public CarService getCarService() {
        return new CarService();
    }
    public InvoiceService getInvoiceService() {
        return new InvoiceService();
    }
    public UserService getUserService() {
        return new UserService();
    }

}
