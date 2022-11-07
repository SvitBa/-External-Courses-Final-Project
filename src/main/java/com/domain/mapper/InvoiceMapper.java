package com.domain.mapper;


import com.database.entity.InvoiceEntity;
import com.domain.model.Invoice;
import org.mapstruct.Mapper;

@Mapper
public interface InvoiceMapper {

    InvoiceEntity invoiceToInvoiceEntity(Invoice invoice);

    Invoice invoiceEntityToInvoice(InvoiceEntity invoiceEntity);
}
