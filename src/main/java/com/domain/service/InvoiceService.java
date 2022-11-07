package com.domain.service;

import com.database.entity.InvoiceEntity;
import com.database.repository.InvoiceDAO;
import com.database.repository.InvoiceRepository;
import com.domain.mapper.InvoiceMapper;
import com.domain.model.Invoice;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceService implements Service<Invoice, Integer> {

    private final InvoiceDAO invoiceRepository;
    private final InvoiceMapper mapper = Mappers.getMapper(InvoiceMapper.class);

    public InvoiceService() {
        this.invoiceRepository = new InvoiceRepository();
    }

    @Override
    public void create(Invoice invoice) {
        InvoiceEntity invoiceEntity = mapper.invoiceToInvoiceEntity(invoice);
        invoiceRepository.createInvoice(invoiceEntity);
    }

    @Override
    public Invoice read(Integer id) {
        return mapper.invoiceEntityToInvoice(invoiceRepository.getInvoiceById(id));
    }

    @Override
    public List<Invoice> readAll() {
        return invoiceRepository.getAllInvoice().stream()
                .map(mapper::invoiceEntityToInvoice)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Invoice invoice) {
        InvoiceEntity invoiceEntity = mapper.invoiceToInvoiceEntity(invoice);
        invoiceRepository.updateInvoice(invoiceEntity);
    }

    @Override
    public void delete(Invoice invoice) {
        invoiceRepository.deleteInvoiceById(invoice.getId());
    }

    public List<Invoice> findInvoiceByBooking(Integer id) {
        return invoiceRepository.getInvoiceByBookingId(id).stream()
                .map(mapper::invoiceEntityToInvoice)
                .collect(Collectors.toList());
    }

    public Invoice updateState(Invoice invoice) {
        InvoiceEntity invoiceEntity = mapper.invoiceToInvoiceEntity(invoice);
        InvoiceEntity updatedInvoice = invoiceRepository.updateInvoiceStatus(invoiceEntity);
        return mapper.invoiceEntityToInvoice(updatedInvoice);
    }
}
