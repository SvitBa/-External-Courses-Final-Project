import com.database.entity.BookingEntity;
import com.database.entity.InvoiceEntity;
import com.domain.mapper.InvoiceMapper;
import com.domain.model.Booking;
import com.domain.model.Invoice;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class InvoiceMapperIntegrationTest {

    private InvoiceMapper mapper
            = Mappers.getMapper(InvoiceMapper.class);

    @Test
    public void givenInvoiceToInvoiceEntity_whenMaps_thenCorrect() {
        Invoice invoice = new Invoice();
        invoice.setType("REGULAR");
        invoice.setStatus("PROCEED");
        invoice.setTotalPrice(BigDecimal.valueOf(250));

        Booking booking= new Booking();
        booking.setBookingStatusCode(1);
        Date pickUpDate = Date.valueOf("2022-10-28");
        Date returnDate = Date.valueOf("2022-10-30");
        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        invoice.setBooking(booking);

        InvoiceEntity invoiceEntity = mapper.invoiceToInvoiceEntity(invoice);

        assertEquals(invoice.getType(), invoiceEntity.getType());
        assertEquals(invoice.getStatus(), invoiceEntity.getStatus());
        assertEquals(invoice.getTotalPrice(), invoiceEntity.getTotalPrice());
        assertEquals(invoice.getBooking().getPickUpDate(), invoiceEntity.getBooking().getPickUpDate());
        assertEquals(invoice.getBooking().getReturnDate(), invoiceEntity.getBooking().getReturnDate());
        assertEquals(invoice.getBooking().getBookingStatusCode(), invoiceEntity.getBooking().getBookingStatusCode());
    }

    @Test
    public void givenInvoiceEntityToInvoice_whenMaps_thenCorrect() {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setType("REGULAR");
        invoiceEntity.setStatus("PROCEED");
        invoiceEntity.setTotalPrice(BigDecimal.valueOf(250));

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setBookingStatusCode(1);
        Date pickUpDate = Date.valueOf("2022-10-28");
        Date returnDate = Date.valueOf("2022-10-30");
        bookingEntity.setTotalPrice(BigDecimal.valueOf(300));
        bookingEntity.setPickUpDate(pickUpDate);
        bookingEntity.setReturnDate(returnDate);
        invoiceEntity.setBooking(bookingEntity);

        Invoice invoice = mapper.invoiceEntityToInvoice(invoiceEntity);

        assertEquals(invoiceEntity.getType(), invoice.getType());
        assertEquals(invoiceEntity.getStatus(), invoice.getStatus());
        assertEquals(invoiceEntity.getTotalPrice(), invoice.getTotalPrice());
        assertEquals(invoiceEntity.getBooking().getPickUpDate(), invoice.getBooking().getPickUpDate());
        assertEquals(invoiceEntity.getBooking().getReturnDate(), invoice.getBooking().getReturnDate());
        assertEquals(invoiceEntity.getBooking().getBookingStatusCode(), invoice.getBooking().getBookingStatusCode());
    }

}
