package com.web;

import com.data.DBException;
import com.data.entity.Booking;
import com.data.entity.Car;
import com.data.repository.BookingRepository;
import com.data.repository.CarModelRepository;
import com.data.repository.CarRepository;

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
    List<Car> carList = null;
    List<String> brandList = null;
    List<String> qualityList = null;

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
        //read command
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LIST";
        }

        // route appropriate method
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
        // step 0: get selected parameter
        String carId = request.getParameter("carId");
        String carAvailable = request.getParameter("carAvailable");
        String bookingId = request.getParameter("bookingId");
        String cancelComment ="-";

        // step1: load existing car object
        Car car = CarRepository.getCarById(Integer.parseInt(carId));

        // step 2: update booking details
        car.setCarCurrentAvailable(Boolean.parseBoolean(carAvailable));

        // step 3: update db
        CarRepository.updateCarCurrentAvailable(car);

        // step 4: load existing booking object
        Booking updateBooking = null;
        try {
            updateBooking = BookingRepository.getBookingById(Integer.parseInt(bookingId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 5: update booking details
        updateBooking.setBookingStatusCode(6);
        updateBooking.setCancelComment(cancelComment);

        // step 6: update db
        BookingRepository.updateBookingStatusCode(updateBooking);

        // step 7: get request dispatcher = send to the BookingServlet
        getServletContext().getRequestDispatcher("/BookingServlet").forward(request, response);

    }

    public void filterCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step 0: get selected parameter
        String brand = request.getParameter("brand");
        String qualityClass = request.getParameter("quality");

        // step 1: get car model from helper class from db
        try {

            if ((brand != null ) & (qualityClass != null)) {
                carList = CarRepository.getCarByQualityAndBrand(qualityClass, brand);
            } else if (brand == null || brand.isBlank()) {
                carList = CarRepository.getCarByQuality(qualityClass);
            } else if (qualityClass == null || qualityClass.isBlank()) {
                carList = CarRepository.getCarByBrand(brand);
            } else {
                carList = CarRepository.getAllCar();
            }
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 2: add car_model to the request object
        request.setAttribute("car_list", carList);
        request.setAttribute("brand_list", brandList);
        request.setAttribute("quality_list", qualityList);

        // step 3: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("car_search.jsp");

        // step 4: forward call jsp
        dispatcher.forward(request, response);
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read car model info from data
        String modelId = request.getParameter("carModelId");
        BigDecimal rentPricePerDay = new BigDecimal(request.getParameter("price"));

        // step2: create new car object
        Car newCar = new Car(rentPricePerDay, modelId);

        // step3: add car model to db
        CarRepository.createCar(newCar);

        // step4: send back to the list page
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarModelServlet");
        dispatcher.forward(request, response);
    }

    public void listCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step 1: get car model from helper class from db
        try {
            carList = CarRepository.getAllCar();
            brandList = CarModelRepository.getBrandList();
            qualityList = CarModelRepository.getQualityList();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 2: add car_model to the request object
        request.setAttribute("car_list", carList);
        request.setAttribute("brand_list", brandList);
        request.setAttribute("quality_list", qualityList);

        // step 3: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("car_search.jsp");

        // step 4: forward call jsp
        dispatcher.forward(request, response);
    }
}
