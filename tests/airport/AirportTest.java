/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airport;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.Saps;

/**
 *
 * @author jonathanwang
 */
public class AirportTest {
    
    public AirportTest() {
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
     * Test of name method, of class Airport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testName_String() {
        System.out.println("name");
        String name = "";
        Airport instance = new Airport();
//        try {
            instance.name(name);
            fail("Unable to catch the IllegalArgumentException");
//        } catch (IllegalArgumentException i) {
//            System.out.println("Invalid manufacturer");
//        }
    }

    /**
     * Test of name method, of class Airport.
     */
    @Test(expected = AssertionError.class)
    public void testName_0args() {
        System.out.println("get airport name");
        Airport instance = new Airport();
        instance.name("JFK");
        String expResult = "BOS";
        String result = instance.name();
        assertEquals(expResult, result);
        fail("Unable to catch the AssertionError");
    }

    /**
     * Test of code method, of class Airport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCode_String() {
        System.out.println("code");
        String code = "";
        Airport instance = new Airport();
//        try {
            instance.code(code);
            fail("Unable to catch the IllegalArgumentException");
//        } catch (IllegalArgumentException i) {
//            System.out.println("Invalid manufacturer");
//        }
    }

    /**
     * Test of code method, of class Airport.
     */
    @Test(expected = AssertionError.class)
    public void testCode_0args() {
        System.out.println("get airport code");
        Airport instance = new Airport();
        instance.code("111");
        String expResult = "";
        String result = instance.code();
        assertEquals(expResult, result);
        fail("Unable to catch the AssertionError");
    }

    /**
     * Test of latitude method, of class Airport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLatitude_double() {
        System.out.println("latitude");
        double latitude = Saps.MAX_LATITUDE + 1;
        Airport instance = new Airport();
//        try {
            instance.latitude(latitude);
            fail("Unable to catch the IllegalArgumentException");
//        } catch (IllegalArgumentException i) {
//            System.out.println("Invalid manufacturer");
//        }
    }

    /**
     * Test of latitude method, of class Airport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLatitude_String() {
        System.out.println("latitude");
        String latitude = "";
        Airport instance = new Airport();
        instance.latitude(latitude);
        // TODO review the generated test code and remove the default call to fail.
        fail("Unable to catch the IllegalArgumentException");
    }

    /**
     * Test of latitude method, of class Airport.
     */
    @Test(expected = AssertionError.class)
    public void testLatitude_0args() {
        System.out.println("latitude");
        Airport instance = new Airport();
        double expResult = 0.0;
        double result = instance.latitude();
        assertEquals(expResult, result, 0.0);
        fail("Unable to catch the AssertionError");    
    }

    /**
     * Test of longitude method, of class Airport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLongitude_double() {
        System.out.println("longitude");
        double longitude = Saps.MAX_LONGITUDE + 1;
        Airport instance = new Airport();
//        try {
            instance.longitude(longitude);
            fail("Unable to catch the IllegalArgumentException");
//        } catch (IllegalArgumentException i) {
//            System.out.println("Invalid manufacturer");
//        }
    }

    /**
     * Test of longitude method, of class Airport.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLongitude_String() {
        System.out.println("longitude");
        String longitude = "";
        Airport instance = new Airport();
        instance.longitude(longitude);
        fail("Unable to catch the IllegalArgumentException");
    }

    /**
     * Test of longitude method, of class Airport.
     */
    @Test(expected = AssertionError.class)
    public void testLongitude_0args() {
        System.out.println("longitude");
        Airport instance = new Airport();
        double expResult = 0.0;
        double result = instance.longitude();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("Unable to catch the AssertionError");
    }
    
}
