/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flight;

import java.io.File;
import java.io.IOException;
//import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author yuey
 */
public class ReserveFlightTest {
    
    public ReserveFlightTest() {
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
     * Test of addSeat method, of class ReserveFlight.
     */
    @Test
    public void testAddSeat() {
        System.out.println("addSeat");
        String flightNumber = "";
        String seatType = "";
//        ReserveFlight instance = new ReserveFlight();
//        instance.addSeat(flightNumber, seatType);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getXML method, of class ReserveFlight.
     */
    @Test
    public void testGetXML() {
        System.out.println("getXML");
        ReserveFlight instance = new ReserveFlight();
        String expResult = "";
        String result = instance.getXML();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
     @Test
    public void testGetXMLContent() {
        System.out.println("getXMLContent");
        ReserveFlight instance = new ReserveFlight();
        instance.addSeat("123", "Coach");
        instance.addSeat("345", "Coach");

        String expResult = "<Flights>\n"
                 + "<Flight number=\"123\" seating=\"Coach\"/>\n"
                 + "<Flight number=\"345\" seating=\"Coach\"/>\n"
                 + "</Flights>";
        String result = instance.getXML();
        assert (expResult.equals(result));
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    public static void stringToDom(String xmlSource)
            throws IOException {
        try (java.io.FileWriter fw = new java.io.FileWriter("flight.xml")) {
            fw.write(xmlSource);
        }
    }
    
     @Test
    public void testGetXMLFormat() throws IOException, ParserConfigurationException, SAXException  {
        System.out.println("GetXMLFormat");
        ReserveFlight instance = new ReserveFlight();
        instance.addSeat("123", "Coach");
        instance.addSeat("345", "Coach");

        String result = instance.getXML();
        
        stringToDom(result);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();

        // the "parse" method also validates XML, will throw an exception if misformatted
        Document document = builder.parse(new InputSource("flight.xml"));
        
       
      

      
    }
}
