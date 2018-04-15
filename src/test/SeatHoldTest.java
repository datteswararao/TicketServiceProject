package test;

import com.walmart.tickets.SeatHold;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class SeatHoldTest {

    SeatHold seatHoldObj;

    @org.junit.Before
    public void setUp() throws Exception {
        List seats = new ArrayList<String>();
        seats.add("1A");
        seatHoldObj = new SeatHold("dattu@dattu.com", seats);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        seatHoldObj = null;
    }

    @org.junit.Test
    public void testSeatsHold() throws Exception {

        Assert.assertEquals(seatHoldObj.getEmailId(), "dattu@dattu.com");
        Assert.assertEquals(seatHoldObj.getSeats().get(0), "1A");

    }


}