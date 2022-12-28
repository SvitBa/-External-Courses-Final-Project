package com.web;

import com.database.entity.BookingEntity;
import com.database.entity.CarEntity;
import com.database.entity.UserEntity;
import com.database.repository.*;

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

    private List<BookingEntity> bookingList = null;

    private BookingDAO bookingRepository = new BookingRepository();
    private UserDAO userRepository = new UserRepository();

    private CarDAO carRepository = new CarRepository();

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
        String userId = request.getParameter("userId");
        String carId = request.getParameter("carId");

        CarEntity selectedCar = carRepository.getCarById(Integer.parseInt(carId));

        request.setAttribute("USER_ID", userId);
        request.setAttribute("SELECTED_CAR", selectedCar);

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_booking.jsp");

        dispatcher.forward(request, response);
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carId = request.getParameter("carId");
        String pickUpDate = request.getParameter("pickUpDate");
        String returnDate = request.getParameter("returnDate");
        String driver = request.getParameter("driver");
        String bookingId = request.getParameter("bookingId");

        BookingEntity updateBooking = null;
        updateBooking = bookingRepository.getBookingById(Integer.parseInt(bookingId));

        CarEntity car = carRepository.getCarById(Integer.parseInt(carId));

        updateBooking.setCar(car);
        updateBooking.setPickUpDate(Date.valueOf(pickUpDate));
        updateBooking.setReturnDate(Date.valueOf(returnDate));
        updateBooking.setDriver(Boolean.parseBoolean(driver));

        bookingRepository.updateBookingDetails(updateBooking);
        listUserBooking(request, response);

    }

    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String userId = request.getParameter("userId");

        bookingRepository.deleteBookingById(Integer.parseInt(bookingId));

        request.setAttribute("USER_ID", userId);
        listUserBooking(request, response);
    }

    private void addBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String carId = request.getParameter("carId");
        String pickUpDate = request.getParameter("pickUpDate");
        String returnDate = request.getParameter("returnDate");
        String driver = request.getParameter("driver");
        String bookingId = request.getParameter("bookingId");

        BookingEntity newBooking = new BookingEntity();

        CarEntity car = carRepository.getCarById(Integer.parseInt(carId));
        UserEntity user = userRepository.getUserById(Integer.parseInt(userId));
        newBooking.setUser(user);

        newBooking.setCar(car);
        newBooking.setPickUpDate(Date.valueOf(pickUpDate));
        newBooking.setReturnDate(Date.valueOf(returnDate));
        newBooking.setDriver(Boolean.parseBoolean(driver));

        bookingRepository.createBooking(newBooking);

        listUserBooking(request, response);
    }

    private void approveBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String cancelComment = "-";
        String carId = request.getParameter("carId");
        String carAvailable = request.getParameter("carAvailable");

        BookingEntity updateBooking = bookingRepository.getBookingById(Integer.parseInt(bookingId));

        updateBooking.setBookingStatusCode(4);
        updateBooking.setCancelComment(cancelComment);

        bookingRepository.updateBookingStatusCode(updateBooking);

        CarEntity car = carRepository.getCarById(Integer.parseInt(carId));

        car.setCarCurrentAvailable(Boolean.parseBoolean(carAvailable));

        carRepository.updateCarCurrentAvailable(car);
        loadAllBooking(request, response);

    }

    private void cancelBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String cancelComment = request.getParameter("cancelComment");

        BookingEntity updateBooking = null;

        updateBooking = bookingRepository.getBookingById(Integer.parseInt(bookingId));

        updateBooking.setBookingStatusCode(3);
        updateBooking.setCancelComment(cancelComment);

        bookingRepository.updateBookingStatusCode(updateBooking);
        loadAllBooking(request, response);

    }

    public void listUserBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        bookingList = bookingRepository.getAllBookingByUserId(Integer.parseInt(userId));

        request.setAttribute("booking_list", bookingList);
        request.setAttribute("USER_ID", userId);

        RequestDispatcher dispatcher = request.getRequestDispatcher("my_profile.jsp");
        dispatcher.forward(request, response);

    }

    public void loadAllBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bookingList = bookingRepository.getAllBooking();
        request.setAttribute("manager_booking_list", bookingList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("manage_booking.jsp");
        dispatcher.forward(request, response);
    }
}
