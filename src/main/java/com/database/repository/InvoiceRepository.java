package com.database.repository;

import com.database.DataBaseConnection;
import com.database.entity.BookingEntity;
import com.database.entity.InvoiceEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository implements InvoiceDAO {

    static final Logger logger = Logger.getLogger(InvoiceRepository.class);
    private static final String CREATE_INVOICE = "INSERT INTO invoice (invoice_type, price, booking_id) VALUES (?, ?, ?)";
    private static final String FIND_ALL_INVOICE = "SELECT * FROM invoice ";
    private static final String FIND_INVOICE_BY_ID = FIND_ALL_INVOICE + " WHERE invoice_id = ?";
    private static final String FIND_INVOICE_BY_BOOKING_ID = FIND_ALL_INVOICE + " WHERE booking_id = ?";
    private static final String UPDATE_INVOICE = "UPDATE invoice SET status = ?, invoice_type =?, price = ? WHERE invoice_id = ?";
    private static final String UPDATE_INVOICE_STATUS = "UPDATE invoice SET status = ? WHERE invoice_id = ?";
    private static final String DELETE_INVOICE = "DELETE FROM invoice WHERE invoice_id = ?";
    private BookingDAO bookingRepository = new BookingRepository();

    @Override
    public void createInvoice(InvoiceEntity newInvoice) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_INVOICE)) {
            statement.setString(1, newInvoice.getType());
            statement.setBigDecimal(2, newInvoice.getTotalPrice());
            statement.setInt(3, newInvoice.getBooking().getId());
            statement.execute();
            logger.info("InvoiceRepository create invoice");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while creating the invoice");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
    }

    @Override
    public List<InvoiceEntity> getAllInvoice() {
        List<InvoiceEntity> invoiceList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(FIND_ALL_INVOICE)) {
            while (resultSet.next()) {
                InvoiceEntity newInvoice = extractInvoiceFromResultSet(resultSet);
                invoiceList.add(newInvoice);
            }
            logger.info("InvoiceRepository find all existing invoice");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while getting all invoice");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
        return invoiceList;
    }

    @Override
    public InvoiceEntity getInvoiceById(int id) {
        InvoiceEntity invoice = null;
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_INVOICE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                invoice = extractInvoiceFromResultSet(resultSet);
                logger.info("InvoiceRepository find invoice by id");
            }
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while getting the invoice by id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
        return invoice;
    }

    @Override
    public List<InvoiceEntity> getInvoiceByBookingId(int id) {
        List<InvoiceEntity> invoiceList = new ArrayList<>();
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_INVOICE_BY_BOOKING_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                InvoiceEntity invoice = extractInvoiceFromResultSet(resultSet);
                invoiceList.add(invoice);
            }
            logger.info("InvoiceRepository find invoices by booking id");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while getting invoices by booking id");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
        return invoiceList;
    }

    @Override
    public InvoiceEntity updateInvoice(InvoiceEntity newInvoice) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INVOICE)) {
            statement.setString(1, newInvoice.getStatus());
            statement.setString(2, newInvoice.getType());
            statement.setBigDecimal(3, newInvoice.getTotalPrice());
            statement.setInt(4, newInvoice.getId());
            statement.executeUpdate();
            logger.info("InvoiceRepository update invoice");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while updating the invoice");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
        return newInvoice;
    }

    @Override
    public InvoiceEntity updateInvoiceStatus(InvoiceEntity newInvoice) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_INVOICE_STATUS)) {
            statement.setString(1, newInvoice.getStatus());
            statement.setInt(2, newInvoice.getId());
            statement.executeUpdate();
            logger.info("InvoiceRepository update invoice status");
        } catch (SQLException e) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while updating the invoice status");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
        return newInvoice;
    }

    @Override
    public void deleteInvoiceById(int id) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_INVOICE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("InvoiceRepository delete invoice");
        } catch (SQLException throwable) {
            DataBaseConnection.rollback(connection);
            logger.error("InvoiceRepository got error while deleting the invoice");
        } finally {
            DataBaseConnection.close(connection);
            logger.info("InvoiceRepository closed connection");
        }
    }

    private InvoiceEntity extractInvoiceFromResultSet(ResultSet resultSet) throws SQLException {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setId(resultSet.getInt("invoice_id"));
        invoice.setType(resultSet.getString("invoice_type"));
        invoice.setStatus(resultSet.getString("status"));
        BookingEntity booking = bookingRepository.getBookingById(resultSet.getInt("booking_id"));
        invoice.setBooking(booking);
        invoice.setTotalPrice(resultSet.getBigDecimal("price"));
        return invoice;
    }
}
