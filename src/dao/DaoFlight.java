/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import flight.*;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.util.logging.Logger;

/**
 * Class responsible for parsing Flight XML and creating Flights object
 * @author alvin-arch
 * 
 */
public class DaoFlight {
    static private Logger flightLogger;
    static {
        flightLogger = Logger.getLogger(DaoFlight.class.getName());
        flightLogger.setLevel(Level.INFO);
    }
    /**
     * Parses XML and generated Flight objects
     * @param xmlFlights: xml from the server
     * @return Flights object
     * @throws NullPointerException 
     */
    public static Flights addAll (String xmlFlights) throws NullPointerException {
        Flights flights = new Flights();
        Document docFlights = buildDomDoc(xmlFlights);
        NodeList nodesFlights = docFlights.getElementsByTagName("Flight");
        
        for (int i = 0; i < nodesFlights.getLength(); ++i) {
            Element elementFlight = (Element) nodesFlights.item(i);
            Flight flight = buildFlight(elementFlight);
            if (flight != null) {
                flights.add(flight);
            }
        }
        return flights;
    }
    
    private static Flight buildFlight(Node nodeFlight) {
        Level logLevel = Level.FINE;
        String buf;
        String plane; // attr
        int flightTimeMins; // attr
        String flightNum; // attr
        // <Departure>
        String departAirportCode; // element
        String departTimeStr; // element
        LocalDateTime departTime;
        String arrivalAirportCode;
        String arrivalTimeStr;
        LocalDateTime arrivalTime;
        String firstClsPriceStr;
        double firstClsPrice;
        int firstClsSeatNum;
        String coachClsPriceStr;
        double coachClsPrice;
        int coachClsSeatNum;
        
        Element elementFlight = (Element) nodeFlight;
        plane = elementFlight.getAttributeNode("Airplane").getValue();
        flightLogger.log(logLevel, "plane={0}", plane);
        flightNum = elementFlight.getAttributeNode("Number").getValue();
        flightLogger.log(logLevel, "flightNum={0}", flightNum);
        buf = elementFlight.getAttributeNode("FlightTime").getValue();
        buf = buf.trim();
        try {
            flightTimeMins = Integer.parseInt(buf);
            flightLogger.log(logLevel, "flightTimeMins={0}", flightTimeMins);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        String dateTimePttrn = "yyyy MMM dd HH:mm zzz";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePttrn);
        
        Element departInfo = (Element)elementFlight.getElementsByTagName("Departure").item(0);
        Element departCodeElement = (Element) departInfo.getElementsByTagName("Code").item(0);
        departAirportCode = getCharacterDataFromElement(departCodeElement);
        flightLogger.log(logLevel, "departAirportCode={0}", departAirportCode);
        Element departTimeElement = (Element) departInfo.getElementsByTagName("Time").item(0);
        departTimeStr = getCharacterDataFromElement(departTimeElement);
        flightLogger.log(logLevel, "departTimeStr={0}", departTimeStr);
        
        Element arrivalInfo = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
        Element arrivalCodeElement = (Element) arrivalInfo.getElementsByTagName("Code").item(0);
        arrivalAirportCode = getCharacterDataFromElement(arrivalCodeElement);
        flightLogger.log(logLevel, "departAirportCode={0}", arrivalAirportCode);
        Element arrivalTimeElement = (Element) arrivalInfo.getElementsByTagName("Time").item(0);
        arrivalTimeStr = getCharacterDataFromElement(arrivalTimeElement);
        flightLogger.log(logLevel, "departTimeStr={0}", arrivalTimeStr);
        
        try {
            departTime = LocalDateTime.parse(departTimeStr, formatter);
            flightLogger.log(logLevel, "departTime={0}", departTime);
            arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);
            flightLogger.log(logLevel, "arrivalTime={0}", arrivalTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        Element seatElement = (Element)elementFlight.getElementsByTagName("Seating").item(0);
        Element firstClsElement = (Element) seatElement.getElementsByTagName("FirstClass").item(0);
        firstClsPriceStr = firstClsElement.getAttributeNode("Price").getValue();
        firstClsPriceStr = firstClsPriceStr.trim();
        firstClsPriceStr = firstClsPriceStr.substring(1, firstClsPriceStr.length());
        firstClsPriceStr = firstClsPriceStr.replaceAll(",", "");
        buf = getCharacterDataFromElement(firstClsElement);
        buf = buf.trim();
        buf = buf.replace(",", "");
        try {
            firstClsSeatNum = Integer.parseInt(buf);
            firstClsPrice = Double.parseDouble(firstClsPriceStr);
            flightLogger.log(logLevel, "firstClsSeatNum={0} firstClsPrice={1}", 
                    new Object[]{firstClsSeatNum, firstClsPrice});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Element coachClsElement = (Element) seatElement.getElementsByTagName("Coach").item(0);
        coachClsPriceStr = coachClsElement.getAttributeNode("Price").getValue();
        coachClsPriceStr = coachClsPriceStr.trim();
        coachClsPriceStr = coachClsPriceStr.substring(1, coachClsPriceStr.length());
        coachClsPriceStr = coachClsPriceStr.replaceAll(",", "");
        buf = getCharacterDataFromElement(coachClsElement);
        buf = buf.trim();
        buf = buf.replace(",", "");
        try {
            coachClsSeatNum = Integer.parseInt(buf);
            coachClsPrice = Double.parseDouble(coachClsPriceStr);
            flightLogger.log(logLevel, "coachClsSeatNum={0} coachClsPrice={1}", 
                    new Object[]{coachClsSeatNum, coachClsPrice});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        flightLogger.log(logLevel, "all set! start creating new Flight instance...");
        Flight ret = null;
        try {
            ret = new Flight(plane, flightTimeMins, flightNum, departAirportCode,
                departTime, arrivalAirportCode, arrivalTime, firstClsPrice, firstClsSeatNum,
                coachClsPrice, coachClsSeatNum);
        } catch (Exception e) {
            flightLogger.log(Level.WARNING, "something wrong when creating new Flight instance!");
            flightLogger.log(Level.WARNING, e.toString());
            e.printStackTrace();
        }
        return ret;
    }
    
    	/**
	 * Builds a DOM tree from an XML string
	 * 
	 * Parses the XML file and returns a DOM tree that can be processed
	 * 
	 * @param xmlString XML String containing set of objects
	 * @return DOM tree from parsed XML or null if exception is caught
	 */
	static private Document buildDomDoc (String xmlString) {
		/**
		 * load the xml string into a DOM document and return the Document
		 */
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlString));
			
			return docBuilder.parse(inputSource);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrieve character data from an element if it exists
	 * 
	 * @param e is the DOM Element to retrieve character data from
	 * @return the character data as String [possibly empty String]
	 */
	private static String getCharacterDataFromElement (Element e) {
		Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	        CharacterData cd = (CharacterData) child;
	        return cd.getData();
	      }
	      return "";
	}
}
