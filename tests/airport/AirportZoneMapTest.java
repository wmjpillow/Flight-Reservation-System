/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airport;

import java.time.ZoneId;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import dao.ServerInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author yuey
 */
public class AirportZoneMapTest {
    
    public AirportZoneMapTest() {
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

    /**
     * Test of GetTimeZoneByAiport method, of class AirportZoneMap.
     */
    @Test
    public void testGetTimeZoneByAiport() {
        System.out.println("GetTimeZoneByAiport");
//        Airport airport = new Airport();
        Airports airports = ServerInterface.INSTANCE.getAirports("CS509team1");
//        List timezoneCheckList = new ArrayList();
        List<String>  timezoneCheckList = Arrays.asList("America/New_York", 
                "America/New_York", "America/New_York", "America/Los_Angeles");

        int tID = 0;
        for (int i = 0; i < 52; i += 15){
            Airport ap = airports.get(i);
            String code = ap.code();
            System.out.println(code);
            String expResult = timezoneCheckList.get(tID);
            tID += 1;
            ZoneId result = AirportZoneMap.GetTimeZoneByAiport(ap);
            
            assertEquals(expResult, result.toString());

            
        }
       
        
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
