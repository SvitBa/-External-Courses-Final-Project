package com.database.repository;

import com.database.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceDAO {

    void createInvoice(InvoiceEntity newInvoice);

    List<InvoiceEntity> getAllInvoice();

    InvoiceEntity getInvoiceById(int id);

    List<InvoiceEntity> getInvoiceByBookingId(int id);


    InvoiceEntity updateInvoice(InvoiceEntity newInvoice);

    InvoiceEntity updateInvoiceStatus(InvoiceEntity newInvoice);

    void deleteInvoiceById(int id);
}
