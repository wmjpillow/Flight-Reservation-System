/**
 * 
 */
package utils;

/**
 * @author blake
 * 
 * System Adaptive Parameters (SAPS)
 * 
 * Constants and values for limits, bounds and configuration of system
 *
 */
public class Saps {
	/**
	 * Constant values used for latitude and longitude range validation
	 */
	public static final double MAX_LATITUDE = 90.0;
	public static final double MIN_LATITUDE = -90.0;
	public static final double MAX_LONGITUDE = 180.0;
	public static final double MIN_LONGITUDE = -180.0;
	
	public static final int MIN_YEAR = 2017;
	public static final int MAX_YEAR = 2018;
	
	public static final int MIN_MONTH = 1;
	public static final int MAX_MONTH = 12;
	
	public static final int MIN_DAY = 1;
	public static final int MAX_DAY = 31;
	
	public static final int MIN_HOUR = 0;
	public static final int MAX_HOUR = 23;
	
	public static final int MIN_MINUTE = 0;
	public static final int MAX_MINUTE = 59;
	
	public static final double MIN_PRICE = 0;
	
	public static final int MIN_SEAT = 0;
	public static final int MAX_SEAT = Integer.MAX_VALUE;
        
        public static final String COACH_TYPE = "Coach";
        public static final String FIRST_CLASS_TYPE = "FirstClass";
	
	
}
