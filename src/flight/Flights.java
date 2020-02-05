/**
 * 
 */
package flight;

import java.util.ArrayList;

/**
 * This class aggregates a number of Flightsdep. The aggregate is implemented as an ArrayList.
 * Flightdep can be added to the aggregate via XML strings in the format returned form the 
 * CS509 server, or as Flightdep objects using the ArrayList interface. Objects can 
 * be removed from the collection using the ArrayList interface.
 * 
  * @author blake
 *
 */
public class Flights extends ArrayList<Flight> {
	private static final long serialVersionUID = 1L;
	
	
}
