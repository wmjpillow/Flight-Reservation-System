/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jonathanwang
 */
public class AirplaneTest {
    
    public AirplaneTest() {
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
     * Test of getmManufacturer method, of class Airplane.
     */
    @Test(expected = AssertionError.class)
    public void testGetmManufacturer() {
        System.out.println("getmManufacturer");
        Airplane instance = new Airplane();
        String expResult = "";
        String result = instance.getmManufacturer();
        assertEquals(expResult, result);
        fail("Unable to catch the AssertionError");
    }

    /**
     * Test of setmManufacturer method, of class Airplane.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetmManufacturer() {
        System.out.println("setmManufacturer");
        String mManufacturer = "";
        Airplane instance = new Airplane();
//        try {
            instance.setmManufacturer(mManufacturer);
            fail("Unable to catch the IllegalArgumentException");
//        } catch (IllegalArgumentException i) {
//            System.out.println("Invalid manufacturer");
//        }
    }

    /**
     * Test of getmModel method, of class Airplane.
     */
    @Test(expected = AssertionError.class)
    public void testGetmModel() {
        System.out.println("getmModel");
        Airplane instance = new Airplane();
        String expResult = "";
        String result = instance.getmModel();
        assertEquals(expResult, result);
        fail("Unable to catch the AssertionError");
    }

    /**
     * Test of setmModel method, of class Airplane.
     */
    @Test
    public void testSetmModel() {
        System.out.println("setmModel");
        String mModel = "";
        Airplane instance = new Airplane();
        try {
            instance.setmModel(mModel);
            fail("Unable to catch the IllegalArgumentException");
        } catch (IllegalArgumentException i) {
            System.out.println("Invalid model");
        }
    }

    /**
     * Test of getmFirstClassSeats method, of class Airplane.
     */
    @Test(expected = AssertionError.class)
    public void testGetmFirstClassSeats() {
        System.out.println("getmFirstClassSeats");
        Airplane instance = new Airplane();
        int expResult = 0;
        int result = instance.getmFirstClassSeats();
        assertEquals(expResult, result);
        fail("Unable to catch the AssertionError");
    }

    /**
     * Test of setmFirstClassSeats method, of class Airplane.
     */
    @Test
    public void testSetmFirstClassSeats() {
        System.out.println("setmFirstClassSeats");
        int mFirstClassSeats = -1;
        Airplane instance = new Airplane();
        try {
            instance.setmFirstClassSeats(mFirstClassSeats);
            fail("Unable to catch the IllegalArgumentException");
        } catch (IllegalArgumentException i) {
            System.out.println("Invalid firstclass seats");
        }
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getmCoachSeats method, of class Airplane.
     */
    @Test(expected = AssertionError.class)
    public void testGetmCoachSeats() {
        System.out.println("getmCoachSeats");
        Airplane instance = new Airplane();
        int expResult = 0;
        int result = instance.getmCoachSeats();
        assertEquals(expResult, result);
        fail("Unable to catch the AssertionError");
    }

    /**
     * Test of setmCoachSeats method, of class Airplane.
     */
    @Test
    public void testSetmCoachSeats() {
        System.out.println("setmCoachSeats");
        int mCoachSeats = -1;
        Airplane instance = new Airplane();
        // TODO review the generated test code and remove the default call to fail.
        try {
            instance.setmCoachSeats(mCoachSeats);
            fail("Unable to catch the IllegalArgumentException");
        } catch (IllegalArgumentException i) {
            System.out.println("Invalid coachclass seats");
        }
    }

    /**
     * Test of toString method, of class Airplane.
     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        Airplane instance = new Airplane();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of compare method, of class Airplane.
     */
//    @Test
//    public void testCompare() {
//        System.out.println("compare");
//        Airplane o1 = null;
//        Airplane o2 = null;
//        Airplane instance = new Airplane();
//        int expResult = 0;
//        int result = instance.compare(o1, o2);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of compareTo method, of class Airplane.
     */
//    @Test
//    public void testCompareTo() {
//        System.out.println("compareTo");
//        Airplane o = null;
//        Airplane instance = new Airplane();
//        int expResult = 0;
//        int result = instance.compareTo(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isValid method, of class Airplane.
     */
//    @Test
//    public void testIsValid() {
//        System.out.println("isValid");
//        Airplane instance = new Airplane();
//        boolean expResult = false;
//        boolean result = instance.isValid();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isValidString method, of class Airplane.
     */
//    @Test
//    public void testIsValidString() {
//        System.out.println("isValidString");
//        String str = "";
//        Airplane instance = new Airplane();
//        boolean expResult = false;
//        boolean result = instance.isValidString(str);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isValidSeats method, of class Airplane.
     */
//    @Test
//    public void testIsValidSeats() {
//        System.out.println("isValidSeats");
//        int seats = 0;
//        Airplane instance = new Airplane();
//        boolean expResult = false;
//        boolean result = instance.isValidSeats(seats);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
