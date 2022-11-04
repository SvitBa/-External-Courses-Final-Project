import com.database.entity.BookingEntity;

import org.junit.Test;

import java.sql.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class BookingEntityTest {
    BookingEntity booking = new BookingEntity();

    @Test
    public void testCalculateNumberOfDaysSuccessCasesTesting() {

        Date pickUpDate = Date.valueOf("2022-10-28");
        Date returnDate = Date.valueOf("2022-10-30");

        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        booking.calculateNumberOfDays();
        assertEquals(3, booking.getNumberOfDays());

        pickUpDate = Date.valueOf("2022-10-28");
        returnDate = Date.valueOf("2022-10-28");

        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        booking.calculateNumberOfDays();
        assertEquals(1, booking.getNumberOfDays());

        pickUpDate = Date.valueOf("2022-10-31");
        returnDate = Date.valueOf("2022-11-01");
        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        booking.calculateNumberOfDays();
        assertEquals(2, booking.getNumberOfDays());
    }

    @Test
    public void testCalculateNumberOfDaysFailCasesTesting() {

        Date pickUpDate = Date.valueOf("2022-10-28");
        Date returnDate = Date.valueOf("2022-10-30");

        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        booking.calculateNumberOfDays();
        assertNotEquals("Day of pick up and day of return is count as one day", 2, booking.getNumberOfDays());

        pickUpDate = Date.valueOf("2022-10-28");
        returnDate = Date.valueOf("2022-10-28");

        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        booking.calculateNumberOfDays();
        assertNotEquals("Day of pick up and day of return is count as one day", 0, booking.getNumberOfDays());

        pickUpDate = Date.valueOf("2022-10-30");
        returnDate = Date.valueOf("2022-11-01");
        booking.setPickUpDate(pickUpDate);
        booking.setReturnDate(returnDate);
        booking.calculateNumberOfDays();
        assertNotEquals("October has 31 days. Day of pick up and day of return is count as one day",
                2, booking.getNumberOfDays());
    }

}
