package com.web;

import com.data.DBException;
import com.data.entity.Booking;
import com.data.entity.Invoice;
import com.data.repository.BookingRepository;
import com.data.repository.InvoiceRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/InvoiceServlet")
public class InvoiceServlet extends HttpServlet {

    List<Invoice> invoiceList = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LOAD";
        }

        switch (theCommand) {
            case "ADD":
                addInvoice(request, response);
                break;
            case "LIST":
                listUserInvoice(request, response);
                break;
            case "LOAD":
                loadAllInvoice(request, response);
                break;
            default:
                loadAllInvoice(request, response);
        }
    }

    private void addInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String state = request.getParameter("state");
        String invoiceType = request.getParameter("type");
        String price = request.getParameter("price");

        Invoice newInvoice = new Invoice();

        Booking booking = null;
        try {
            booking = BookingRepository.getBookingById(Integer.parseInt(bookingId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        newInvoice.setBooking(booking);
        newInvoice.setStatus(state);
        newInvoice.setType(invoiceType);
        newInvoice.setTotalPrice(BigDecimal.valueOf(Long.parseLong(price)));

        InvoiceRepository.createInvoice(newInvoice);

        loadAllInvoice(request, response);
    }

    private void prepareInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String invoiceType = request.getParameter("type");
        String price = request.getParameter("price");

        Invoice newInvoice = new Invoice();

        Booking booking = null;
        try {
            booking = BookingRepository.getBookingById(Integer.parseInt(bookingId));
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        loadAllInvoice(request, response);
    }

    public void listUserInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        List<Booking> userBookingList = null;
        try {
            userBookingList = BookingRepository.getAllBookingByUserId(Integer.parseInt(userId));
            for (Booking booking : userBookingList) {
                invoiceList.addAll(InvoiceRepository.getInvoiceById(booking.getId()));
            }
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("USER_ID", userId);
        request.setAttribute("invoice_list", invoiceList);
        request.setAttribute("booking_list", userBookingList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_invoice_list.jsp");
        dispatcher.forward(request, response);
    }

    public void loadAllInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            invoiceList = InvoiceRepository.getAllInvoice();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("invoice_list", invoiceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view_invoice_list.jsp");
        dispatcher.forward(request, response);
    }
}
