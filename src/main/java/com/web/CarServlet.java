package com.web;

import com.database.entity.BookingEntity;
import com.database.entity.CarEntity;
import com.database.repository.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/CarServlet")
public class CarServlet extends HttpServlet {
    List<CarEntity> carList = null;
    List<String> brandList = null;
    List<String> qualityList = null;

    private BookingDAO bookingRepository = new BookingRepository();
    private CarDAO carRepository = new CarRepository();

    private CarModelDAO carModelRepository = new CarModelRepository();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LIST";
        }

        switch (theCommand) {
            case "ADD":
                addCar(request, response);
                break;
            case "LIST":
                listCar(request, response);
                break;
            case "FILTER":
                filterCar(request, response);
                break;
            case "RETURN":
                returnCar(request, response);
                break;
            default:
                listCar(request, response);
        }
    }

    public void returnCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carId = request.getParameter("carId");
        String carAvailable = request.getParameter("carAvailable");
        String bookingId = request.getParameter("bookingId");
        String cancelComment = "-";

        CarEntity car = carRepository.getCarById(Integer.parseInt(carId));

        car.setCarCurrentAvailable(Boolean.parseBoolean(carAvailable));

        carRepository.updateCarCurrentAvailable(car);

        BookingEntity updateBooking = null;
        updateBooking = bookingRepository.getBookingById(Integer.parseInt(bookingId));
        updateBooking.setBookingStatusCode(6);
        updateBooking.setCancelComment(cancelComment);

        bookingRepository.updateBookingStatusCode(updateBooking);
        getServletContext().getRequestDispatcher("/BookingServlet").forward(request, response);

    }

    public void filterCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        String qualityClass = request.getParameter("quality");

        if ((brand != null) & (qualityClass != null)) {
            carList = carRepository.getCarByQualityAndBrand(qualityClass, brand);
        } else if (brand == null || brand.isBlank()) {
            carList = carRepository.getCarByQuality(qualityClass);
        } else if (qualityClass == null || qualityClass.isBlank()) {
            carList = carRepository.getCarByBrand(brand);
        } else {
            carList = carRepository.getAllCar();
        }

        request.setAttribute("car_list", carList);
        request.setAttribute("brand_list", brandList);
        request.setAttribute("quality_list", qualityList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("car_search.jsp");
        dispatcher.forward(request, response);
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String modelId = request.getParameter("carModelId");
        BigDecimal rentPricePerDay = new BigDecimal(request.getParameter("price"));

        CarEntity newCar = new CarEntity(rentPricePerDay, modelId);

        carRepository.createCar(newCar);

        RequestDispatcher dispatcher = request.getRequestDispatcher("CarModelServlet");
        dispatcher.forward(request, response);
    }

    public void listCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        carList = carRepository.getAllCar();
        brandList = carModelRepository.getBrandList();
        qualityList = carModelRepository.getQualityList();

        request.setAttribute("car_list", carList);
        request.setAttribute("brand_list", brandList);
        request.setAttribute("quality_list", qualityList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("car_search.jsp");
        dispatcher.forward(request, response);
    }
}
