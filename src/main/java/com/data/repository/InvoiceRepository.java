package com.data.repository;

import com.data.DBException;
import com.data.DataBaseConnection;
import com.data.entity.Booking;
import com.data.entity.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {

    private static final String CREATE_INVOICE = "INSERT INTO invoice (status, invoice_type, price, booking_id) VALUES (?, ?, ?, ?)";

    private static final String FIND_ALL_INVOICE = "SELECT * FROM invoice ";

    private static final String FIND_INVOICE_BY_ID = FIND_ALL_INVOICE + " WHERE invoice_id = ?";

    private static final String UPDATE_INVOICE = "UPDATE invoice SET status = ?, invoice_type =?, price = ? WHERE invoice_id = ?";

    private static final String UPDATE_INVOICE_STATUS = "UPDATE invoice SET status = ? WHERE invoice_id = ?";

    private static final String DELETE_INVOICE = "DELETE FROM invoice WHERE invoice_id = ?";

    public static void createInvoice(Invoice newInvoice) {
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_INVOICE)
        ) {
            statement.setString(1, newInvoice.getStatus());
            statement.setString(2, newInvoice.getType());
            statement.setBigDecimal(3, newInvoice.getTotalPrice());
            statement.setInt(5, newInvoice.getBooking().getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Invoice> getAllInvoice() throws DBException {
        List<Invoice> invoiceList = new ArrayList<>();
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet resultSet = stmt.executeQuery(FIND_ALL_INVOICE)
        ) {
            while (resultSet.next()) {
                Invoice newInvoice = extractInvoiceFromResultSet(resultSet);
                invoiceList.add(newInvoice);
            }
        } catch (SQLException e) {
            throw new DBException("FIND All booking FAIL", e);
        }
        return invoiceList;
    }


    public static List<Invoice> getInvoiceById(int id) throws DBException {
        List<Invoice> invoiceList = null;
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_INVOICE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = extractInvoiceFromResultSet(resultSet);
                invoiceList.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoiceList;
    }

    public static Invoice updateInvoice(Invoice newInvoice) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_INVOICE)
        ) {
            statement.setString(1, newInvoice.getStatus());
            statement.setString(2, newInvoice.getType());
            statement.setBigDecimal(3, newInvoice.getTotalPrice());
            statement.setInt(4, newInvoice.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newInvoice;
    }

    public static Invoice updateInvoiceStatus(Invoice newInvoice) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(UPDATE_INVOICE_STATUS)
        ) {
            statement.setString(1, newInvoice.getStatus());
            statement.setInt(2, newInvoice.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newInvoice;
    }

    public static void deleteInvoiceById(int id) {
        try (Connection con = DataBaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_INVOICE)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private static Invoice extractInvoiceFromResultSet(ResultSet resultSet) throws SQLException, DBException {
        Invoice invoice = new Invoice();
        invoice.setId(resultSet.getInt("invoice_id"));
        invoice.setType(resultSet.getString("invoice_type"));
        invoice.setStatus(resultSet.getString("status"));
        Booking booking = BookingRepository.getBookingById(resultSet.getInt("booking_id"));
        invoice.setBooking(booking);
        invoice.setTotalPrice(resultSet.getBigDecimal("price"));
        return invoice;
    }

}
