package com.web.mapper;


import com.domain.model.Invoice;
import com.web.model.InvoiceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface InvoiceMapperWeb {

    InvoiceDTO invoiceToInvoiceDTO(Invoice invoice);

    Invoice invoiceDTOToInvoice(InvoiceDTO invoiceDTO);
}
