/**
 * 
 */
package dao;

import java.io.IOException;
import java.io.StringReader;

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

import airplane.Airplane;
import airplane.Airplanes;
import airport.Airport;

/**
 * @author blake
 *
 */
public class DaoAirplane {
	/**
	 * Builds collection of airports from airports described in XML
	 * 
	 * Parses an XML string to read each of the airports and adds each valid
	 * airport to the collection. The method uses Java DOM (Document Object
	 * Model) to convert from XML to Java primitives.
	 * 
	 * Method iterates over the set of Airport nodes in the XML string and
	 * builds an Airport object from the XML node string and add the Airport
	 * object instance to the Airports collection.
	 * 
	 * @param xmlAirports
	 *            XML string containing set of airports
	 * @return [possibly empty] collection of Airports in the xml string
	 * @throws NullPointerException
	 *             included to keep signature consistent with other addAll
	 *             methods
	 * 
	 * @pre the xmlAirports string adheres to the format specified by the server
	 *      API
	 * @post the [possibly empty] set of Airports in the XML string are added to
	 *       collection
	 */
	public static Airplanes addAll(String xmlAirplanes) throws NullPointerException {
		Airplanes airplanes = new Airplanes();

		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each airport to our collection
		Document docAirplanes = buildDomDoc(xmlAirplanes);
		NodeList nodesAirplanes = docAirplanes.getElementsByTagName("Airplane");
		for (int i = 0; i < nodesAirplanes.getLength(); i++) {
			Element elementAirplane = (Element) nodesAirplanes.item(i);
			Airplane airplane = buildAirport(elementAirplane);
			if (airplane.isValid()) {
				airplanes.add(airplane);
			}
		}

		return airplanes;
	}

	/**
	 * Creates an Airport object from a DOM node
	 * 
	 * Processes a DOM Node that describes an Airport and creates an Airport
	 * object from the information
	 * 
	 * @param nodeAirport
	 *            is a DOM Node describing an Airport
	 * @return Airport object created from the DOM Node representation of the
	 *         Airport
	 * 
	 * @pre nodeAirport is of format specified by CS509 server API
	 */
	static private Airplane buildAirport(Node nodeAirplane) {
		/**
		 * Instantiate an empty Airport object
		 */
		Airplane airplane = new Airplane();

		String manufacturer;
		String model;
		int firstClassSeats;
		int coachSeats;

		// The airport element has attributes of Name and 3 character airport
		// code
		Element elementAirplane = (Element) nodeAirplane;
		manufacturer = elementAirplane.getAttributeNode("Manufacturer").getValue();
		model = elementAirplane.getAttributeNode("Model").getValue();
		// The latitude and longitude are child elements
		Element elementCoachSeats,
				elementFirstClassSeats = (Element) elementAirplane.getElementsByTagName("FirstClassSeats").item(0);
		firstClassSeats = Integer.parseInt(getCharacterDataFromElement(elementFirstClassSeats));

		elementCoachSeats = (Element) elementAirplane.getElementsByTagName("CoachSeats").item(0);
		coachSeats = Integer.parseInt(getCharacterDataFromElement(elementCoachSeats));

		/**
		 * Update the Airplane object with values from XML node
		 */
		airplane.setmManufacturer(manufacturer);
		airplane.setmModel(model);
		airplane.setmFirstClassSeats(firstClassSeats);
		airplane.setmCoachSeats(coachSeats);
		return airplane;
	}

	/**
	 * Builds a DOM tree from an XML string
	 * 
	 * Parses the XML file and returns a DOM tree that can be processed
	 * 
	 * @param xmlString
	 *            XML String containing set of objects
	 * @return DOM tree from parsed XML or null if exception is caught
	 */
	static private Document buildDomDoc(String xmlString) {
		/**
		 * load the xml string into a DOM document and return the Document
		 */
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlString));

			return docBuilder.parse(inputSource);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieve character data from an element if it exists
	 * 
	 * @param e
	 *            is the DOM Element to retrieve character data from
	 * @return the character data as String [possibly empty String]
	 */
	private static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}
