package com.database.repository;

import com.database.entity.InvoiceEntity;

import java.util.List;

public interface InvoiceDAO {

    void createInvoice(InvoiceEntity newInvoice) ;

    List<InvoiceEntity> getAllInvoice() ;

    List<InvoiceEntity> getInvoiceById(int id) ;

    InvoiceEntity updateInvoice(InvoiceEntity newInvoice) ;

    InvoiceEntity updateInvoiceStatus(InvoiceEntity newInvoice) ;

    void deleteInvoiceById(int id) ;
}
