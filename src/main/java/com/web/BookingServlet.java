package com.web;

import com.data.DBException;
import com.data.entity.Booking;
import com.data.entity.Car;
import com.data.entity.User;
import com.data.repository.BookingRepository;
import com.data.repository.CarRepository;
import com.data.repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    List<Booking> bookingList = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //read command
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LOAD";
        }

        // route appropriate method
        switch (theCommand) {

            case "ADD":
                addBooking(request, response);
                break;
            case "APPROVE":
                approveBooking(request, response);
                break;
            case "CANCEL":
                cancelBooking(request, response);
                break;
            case "PREPARE":
                prepareBooking(request, response);
                break;
            case "DELETE":
                deleteBooking(request, response);
                break;
            case "LIST":
                listUserBooking(request, response);
                break;
            case "LOAD":
                loadAllBooking(request, response);
                break;
            case "UPDATE":
                updateBooking(request, response);
                break;
            default:
                loadAllBooking(request, response);
        }
    }

    private void prepareBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read booking info from data
        String userId = request.getParameter("userId");
        String carId = request.getParameter("carId");

        //step 2: load car details
        Car selectedCar = CarRepository.getCarById(Integer.parseInt(carId));

        // step 3: add booking details to the request object
        request.setAttribute("USER_ID", userId);
        request.setAttribute("SELECTED_CAR", selectedCar);

        // step 4: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_booking.jsp");

        // step 5: forward call jsp
        dispatcher.forward(request, response);
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read Booking data from form
        String carId = request.getParameter("carId");
        String pickUpDate = request.getParameter("pickUpDate");
        String returnDate = request.getParameter("returnDate");
        String driver = request.getParameter("driver");
        String bookingId = request.getParameter("bookingId");

        // step2: load existing booking object
        Booking updateBooking = null;
        try {
            updateBooking = BookingRepository.getBookingById(Integer.parseInt(bookingId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 3: load selected car
        Car car = CarRepository.getCarById(Integer.parseInt(carId));

        // step 4: update booking details
        updateBooking.setCar(car);
        updateBooking.setPickUpDate(Date.valueOf(pickUpDate));
        updateBooking.setReturnDate(Date.valueOf(returnDate));
        updateBooking.setDriver(Boolean.parseBoolean(driver));

        // step 5: update db
        BookingRepository.updateBookingDetails(updateBooking);

        // step 6: send back to the list page
        listUserBooking(request, response);

    }

    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read booking id from data
        String bookingId = request.getParameter("bookingId");
        String userId = request.getParameter("userId");

        // step2:delete booking model id from data
        BookingRepository.deleteBookingById(Integer.parseInt(bookingId));

        // step 3: add booking details to the request object
        request.setAttribute("USER_ID", userId);

        // step3: send back to the list page
        listUserBooking(request, response);
    }

    private void addBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read booking info from data
        String userId = request.getParameter("userId");
        String carId = request.getParameter("carId");
        String pickUpDate = request.getParameter("pickUpDate");
        String returnDate = request.getParameter("returnDate");
        String driver = request.getParameter("driver");
        String bookingId = request.getParameter("bookingId");

        // step2: create new Booking object
        Booking newBooking = new Booking();

        // step 3: load selected car and user
        Car car = CarRepository.getCarById(Integer.parseInt(carId));
        try {
            User user = UserRepository.getUserById(Integer.parseInt(userId));
            newBooking.setUser(user);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 4: add booking details
        newBooking.setCar(car);
        newBooking.setPickUpDate(Date.valueOf(pickUpDate));
        newBooking.setReturnDate(Date.valueOf(returnDate));
        newBooking.setDriver(Boolean.parseBoolean(driver));

        // step3: add booking to db
        BookingRepository.createBooking(newBooking);

        // step4: send back to the list page
        listUserBooking(request, response);
    }

    private void approveBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read booking info from data
        String bookingId = request.getParameter("bookingId");
        String cancelComment ="-";
        String carId = request.getParameter("carId");
        String carAvailable = request.getParameter("carAvailable");

        // step2: load existing booking object
        Booking updateBooking = null;
        try {
            updateBooking = BookingRepository.getBookingById(Integer.parseInt(bookingId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 4: update booking details
        updateBooking.setBookingStatusCode(4);
        updateBooking.setCancelComment(cancelComment);

        // step 5: update db
        BookingRepository.updateBookingStatusCode(updateBooking);

        // step 6: load existing car object
        Car car = CarRepository.getCarById(Integer.parseInt(carId));

        // step 7: update booking details
        car.setCarCurrentAvailable(Boolean.parseBoolean(carAvailable));

        // step 8: update db
        CarRepository.updateCarCurrentAvailable(car);

        // step 9: send back to the list page
        loadAllBooking(request, response);

    }

    private void cancelBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read booking info from data
        String bookingId = request.getParameter("bookingId");
        String cancelComment = request.getParameter("cancelComment");

        // step2: load existing booking object
        Booking updateBooking = null;
        try {
            updateBooking = BookingRepository.getBookingById(Integer.parseInt(bookingId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 4: update booking details
        updateBooking.setBookingStatusCode(3);
        updateBooking.setCancelComment(cancelComment);

        // step 5: update db
        BookingRepository.updateBookingStatusCode(updateBooking);

        // step 6: send back to the list page
        loadAllBooking(request, response);

    }

    public void listUserBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step 0: receive userId info from data
        String userId = request.getParameter("userId");

        // step 1: get booking from helper class from db
        try {
            bookingList = BookingRepository.getAllBookingByUserId(Integer.parseInt(userId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 2: add booking to the request object
        request.setAttribute("booking_list", bookingList);
        request.setAttribute("USER_ID", userId);

        // step 3: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("booking.jsp");

        // step 4: forward call jsp
        dispatcher.forward(request, response);

    }

    public void loadAllBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            bookingList = BookingRepository.getAllBooking();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 2: add booking to the request object
        request.setAttribute("booking_list", bookingList);

        // step 3: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("manage_booking.jsp");

        // step 4: forward call jsp
        dispatcher.forward(request, response);

    }
}
