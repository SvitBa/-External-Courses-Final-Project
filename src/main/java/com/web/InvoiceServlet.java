package com.web;

import com.database.entity.BookingEntity;
import com.database.entity.InvoiceEntity;
import com.database.repository.BookingDAO;
import com.database.repository.BookingRepository;
import com.database.repository.InvoiceDAO;
import com.database.repository.InvoiceRepository;

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

    List<InvoiceEntity> invoiceList = null;
    private BookingDAO bookingRepository = new BookingRepository();
    private InvoiceDAO invoiceRepository = new InvoiceRepository();

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
            case "PAY":
                payInvoice(request, response);
                break;
            default:
                loadAllInvoice(request, response);
        }
    }

    private void payInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String invoiceId = request.getParameter("invoiceId");

        InvoiceEntity invoice = invoiceRepository.getInvoiceById(Integer.parseInt(invoiceId));
        invoice.setStatus("PAID");

        invoiceRepository.updateInvoiceStatus(invoice);

        RequestDispatcher dispatcher = request.getRequestDispatcher("my_profile.jsp");
        dispatcher.forward(request, response);
    }

    private void addInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String invoiceType = request.getParameter("type");
        String price = request.getParameter("price");

        InvoiceEntity newInvoice = new InvoiceEntity();

        BookingEntity booking = bookingRepository.getBookingById(Integer.parseInt(bookingId));

        newInvoice.setBooking(booking);
        newInvoice.setType(invoiceType);
        newInvoice.setTotalPrice(new BigDecimal(price));

        invoiceRepository.createInvoice(newInvoice);

        loadAllInvoice(request, response);
    }

    private void prepareInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String invoiceType = request.getParameter("type");
        String price = request.getParameter("price");

        InvoiceEntity newInvoice = new InvoiceEntity();

        BookingEntity booking = bookingRepository.getBookingById(Integer.parseInt(bookingId));


        loadAllInvoice(request, response);
    }

    public void listUserInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        List<BookingEntity> userBookingList = null;

        userBookingList = bookingRepository.getAllBookingByUserId(Integer.parseInt(userId));
        for (BookingEntity booking : userBookingList) {
            invoiceList.addAll(invoiceRepository.getInvoiceByBookingId(booking.getId()));
        }

        request.setAttribute("USER_ID", userId);
        request.setAttribute("invoice_list", invoiceList);
        request.setAttribute("booking_list", userBookingList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_invoice_list.jsp");
        dispatcher.forward(request, response);
    }

    public void loadAllInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        invoiceList = invoiceRepository.getAllInvoice();
        request.setAttribute("all_invoice_list", invoiceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view_invoice_list.jsp");
        dispatcher.forward(request, response);
    }
}
