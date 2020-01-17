/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightsystem;

import java.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author dg71532
 */
public class TableItemsTest {
    
    public TableItemsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddFlightnumberString() {
        /**
         * Test to verify toString conversion for flightNumbers
         */
        System.out.println("testAddFlightnumberString");
        TableItems tableItems = new TableItems();
        tableItems.flightNumberClass.addFlightNumber("503");
        tableItems.flightNumberClass.addFlightNumber("504");
        String returnString = tableItems.flightNumberClass.toString();
        assert(returnString.equals("503->504"));
    }
    @Test
    public void testDepartureArrivalTimeString() {
        /**
         * Test to verify departure and arrival airport code toString() method
         */
        System.out.println("testDepartureArrivalTimeString");
        TableItems tableItems = new TableItems();
        tableItems.departureArrivalClass.addDepartureArrival("BOS", "CLE", "COACH");
        tableItems.departureArrivalClass.addDepartureArrival("CLE", "GE", "COACH");
        tableItems.departureArrivalClass.addDepartureArrival("GE", "FL", "COACH");
        String resultString = tableItems.departureArrivalClass.toString();
        String expected ="BOS-[COACH]->CLE-[COACH]->GE-[COACH]->FL";
        assert( resultString.equals(expected) );
    }
    
    @Test
    public void testLayoverString() 
    {
        /**
         * Test to verify departure and arrival time toString()
         */
        System.out.println("testDepartureArrivalTimeString");
        TableItems tableItems = new TableItems();
        LocalDateTime departure1 = LocalDateTime.now();
        LocalDateTime arrival1 = LocalDateTime.now().plusHours(1);
        tableItems.departureArrivalTimeClass.addDepartureArrivalTime(departure1, arrival1);
        LocalDateTime departure2 = LocalDateTime.now().plusHours(2);
        LocalDateTime arrival2 = LocalDateTime.now().plusHours(3);
        tableItems.departureArrivalTimeClass.addDepartureArrivalTime(departure2, arrival2);
        LocalDateTime departure3 = LocalDateTime.now().plusHours(5);
        LocalDateTime arrival3 = LocalDateTime.now().plusHours(6);
        tableItems.departureArrivalTimeClass.addDepartureArrivalTime(departure3, arrival3);
        String layoverTimes = tableItems.departureArrivalTimeClass.getLayOverTimes();
        assert( layoverTimes.equals("60,120"));
        
        String resultString = tableItems.departureArrivalTimeClass.toString();
        String expectedString = departure1.toString()+"->"+arrival1.toString()+
                "-LAYOVER-"+
                departure2.toString()+"->"+arrival2.toString()+
                "-LAYOVER-"+
                departure3.toString()+"->"+arrival3.toString();
        assert( resultString.equals(expectedString) );
        
    }
    
    
}
