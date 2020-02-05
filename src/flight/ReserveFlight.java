/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flight;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A class used for reserving flight on server
 * @author yuey
 */
public class ReserveFlight {
    
    HashMap<String, String> seatMap = new HashMap<>(); 
    
    public void addSeat(String flightNumber, String seatType) {
        
        if (!isValidStringInt(flightNumber))
			throw new IllegalArgumentException(flightNumber);
	if (!isValidString(seatType))
			throw new IllegalArgumentException(seatType);
        seatMap.put(flightNumber, seatType);
        
        
    }
    
    public String getXML(){
        String head = "<Flights>\n";
        String end = "</Flights>";
        String token = "";
        
        if (seatMap.isEmpty()){
        return "";
        }
        
        for (Map.Entry<String, String> pair : seatMap.entrySet()) {
            String flightNumber = pair.getKey();
            String seatType = pair.getValue();
            token += "  <Flight number=\"" + flightNumber + "\" seating=\""
                    + seatType + "\"/>\n";

        }
        String xmlData =   head + token + end;
        return xmlData;
    }
    
    private boolean isValidString(String str) {
		if (str == null || str.length() == 0)
			return false;
		return true;
	}
    private boolean isValidStringInt(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
    
    
    
}
