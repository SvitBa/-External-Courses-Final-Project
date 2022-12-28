package com.web;

import com.database.entity.BookingEntity;
import com.database.entity.InvoiceEntity;
import com.database.entity.UserEntity;
import com.database.repository.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private BookingDAO bookingRepository = new BookingRepository();
    private InvoiceDAO invoiceRepository = new InvoiceRepository();
    private UserDAO userRepository = new UserRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank() || password == null || username.isBlank()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

        UserEntity user = userRepository.getUserForLogin(username, password);

        if (user.getId() == 1) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {

            List<InvoiceEntity> invoiceList = new ArrayList<>();
            List<BookingEntity> bookingList = bookingRepository.getAllBookingByUserId(user.getId());

            for (BookingEntity booking : bookingList) {
                List<InvoiceEntity> oneBookingInvoiceList = invoiceRepository.getInvoiceByBookingId(booking.getId());
                if (oneBookingInvoiceList != null) {
                    invoiceList.addAll(oneBookingInvoiceList);
                }
            }

            request.setAttribute("USER_ID", user.getId());
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("booking_list", bookingList);
            request.getSession().setAttribute("invoice_list", invoiceList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("CarServlet");
            dispatcher.forward(request, response);
        }
    }
}
