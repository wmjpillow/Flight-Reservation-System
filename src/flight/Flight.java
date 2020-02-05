/**
 * 
 */
package flight;

import java.util.Comparator;

import utils.Saps;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds values pertaining to a single Flightdep. Class member
 * attributes are the same as defined by the CS509 server API and store values
 * after conversion from XML received from the server to Java primitives.
 * Attributes are accessed via getter and setter methods.
 * 
 * @author Yun
 * @version 1.0
 * @since 2017-10-27
 * 
 * 
 *
 */
public class Flight implements Comparable<Flight>, Comparator<Flight> {

	/**
	 * Flightdep attributes as defined by the CS509 server interface XML
	 */
	private String mAirplane; // Airplane type
	private int mFlightTime; // Time of the flight in minutes
	private String mNumber; // Flight number either 4 or 5 characters
	private String mDepAirport; // 3 character code of departing airport
	private LocalDateTime mDepTime; // Date and time of departure in GMT
	private String mArrAirport; // 3 character code of departing airport
	private LocalDateTime mArrTime; // Date and time of departure in GMT
	private double mPriceFirst; // First class price
	private int mSeatsFirst; // Number of first class seats already reserved
	private double mPriceCoach; // Coach price
	private int mSeatsCoach; // Number of coach seats already reserved
        private List<String> mSeatTypeAvailable;
        private double mAvailableSeatPrice;
	


	/**
	 * Default constructor
	 * 
	 * Constructor without params. Requires object fields to be explicitly set using
	 * setter methods
	 * 
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */

	public Flight() {
		mAirplane = "";
		mFlightTime = Integer.MAX_VALUE;
		mNumber = "";
		mDepAirport = "";
		mDepTime = LocalDateTime.now();
		mArrAirport = "";
		mArrTime = LocalDateTime.now();
		mPriceFirst = Double.MAX_VALUE;
		mSeatsFirst = Integer.MAX_VALUE;
		mPriceCoach = Double.MAX_VALUE;
		mSeatsCoach = Integer.MAX_VALUE;
                mSeatTypeAvailable = new ArrayList<>();
                mAvailableSeatPrice = Double.MAX_VALUE; 
	}

	/**
	 * Initializing constructor.
	 * 
	 * All attributes are initialized with input values
	 * 
	 */
	
	
	

	public Flight(String mAirplane, int mFlightTime, String mNumber, String mDepAirport, LocalDateTime mDepTime,
			String mArrAirport, LocalDateTime mArrTime, double mPriceFirst, int mSeatsFirst, double mPriceCoach,
			int mSeatsCoach) {
		
		if (!isValidmAirplane(mAirplane))
			throw new IllegalArgumentException(mAirplane);
		
		if (!isValidmFlightTime(mFlightTime))
			throw new IllegalArgumentException(Integer.toString(mFlightTime));
		
		if (!isValidmNumber(mNumber))
			throw new IllegalArgumentException(mNumber);
		
		if (!isValidmDepAirport(mDepAirport))
			throw new IllegalArgumentException(mDepAirport);
		
		if (!isValidmDepTime(mDepTime))
			throw new IllegalArgumentException(mDepTime.toString());
		
		if (!isValidmDepAirport(mArrAirport))
			throw new IllegalArgumentException(mArrAirport);
		
		if (!isValidmDepTime(mArrTime))
			throw new IllegalArgumentException(mArrTime.toString());
		
		if (!isValidmPriceFirst(mPriceFirst))
			throw new IllegalArgumentException(Double.toString(mPriceFirst));
		
		if (!isValidmSeatsFirst(mSeatsFirst))
			throw new IllegalArgumentException(Integer.toString(mSeatsFirst));
		
		if (!isValidmPriceFirst(mPriceCoach))
			throw new IllegalArgumentException(Double.toString(mPriceCoach));
		
		if (!isValidmSeatsFirst(mSeatsCoach))
			throw new IllegalArgumentException(Integer.toString(mSeatsCoach));

		
		
		this.mAirplane = mAirplane;
		this.mFlightTime = mFlightTime;
		this.mNumber = mNumber;
		this.mDepAirport = mDepAirport;
		this.mDepTime = mDepTime;
		this.mArrAirport = mArrAirport;
		this.mArrTime = mArrTime;
		this.mPriceFirst = mPriceFirst;
		this.mSeatsFirst = mSeatsFirst;
		this.mPriceCoach = mPriceCoach;
		this.mSeatsCoach = mSeatsCoach;
                this.mSeatTypeAvailable = new ArrayList<>();
	}


	
// getter and setter

	public String getmAirplane() {
		return mAirplane;
	}

	public void setmAirplane(String mAirplane) {
		if (isValidmAirplane (mAirplane))
			this.mAirplane = mAirplane;
		else
			throw new IllegalArgumentException (mAirplane);
	}

	public int getmFlightTime() {
		return mFlightTime;
	}

	public void setmFlightTime(int mFlightTime) {
		if (isValidmFlightTime(mFlightTime))
			this.mFlightTime = mFlightTime;
		else 			
			throw new IllegalArgumentException(Integer.toString(mFlightTime));		
	}

	public String getmNumber() {
		return mNumber;
	}

	public void setmNumber(String mNumber) {
		if (isValidmNumber (mNumber)) 
			this.mNumber = mNumber;
		else
			throw new IllegalArgumentException(mNumber);	
	}

	public String getmDepAirport() {
		return mDepAirport;
	}

	public void setmDepAirport(String mDepAirport) {
		if (isValidmDepAirport (mDepAirport))
			this.mDepAirport = mDepAirport;
		else 
			throw new IllegalArgumentException(mDepAirport);
	}

	public LocalDateTime getmDepTime() {
		return mDepTime;
	}

	public void setmDepTime(LocalDateTime mDepTime) {
		if (isValidmDepTime (mDepTime))
			this.mDepTime = mDepTime;
		else
			throw new IllegalArgumentException(mDepTime.toString());		
	}

	public String getmArrAirport() {
		return mArrAirport;
	}

	public void setmArrAirport(String mArrAirport) {
		if (isValidmDepAirport (mArrAirport))
			this.mArrAirport = mArrAirport;
		else
			throw new IllegalArgumentException(mArrAirport);
	}

	public LocalDateTime getmArrTime() {
		return mArrTime;
	}

	public void setmArrTime(LocalDateTime mArrTime) {
		if (isValidmDepTime (mArrTime))
			this.mArrTime = mArrTime;
		else
			throw new IllegalArgumentException(mArrTime.toString());
	}

	public double getmPriceFirst() {
		return mPriceFirst;
	}

	public void setmPriceFirst(double mPriceFirst) {
		if (isValidmPriceFirst (mPriceFirst))
			this.mPriceFirst = mPriceFirst;
		else
			throw new IllegalArgumentException(Double.toString(mPriceFirst));
	
	}

	public int getmSeatsFirst() {
		return mSeatsFirst;
	}

	public void setmSeatsFirst(int mSeatsFirst) {
		if (isValidmSeatsFirst (mSeatsFirst))
			this.mSeatsFirst = mSeatsFirst;
		else
			throw new IllegalArgumentException(Integer.toString(mSeatsFirst));			
	}

	public double getmPriceCoach() {
		return mPriceCoach;
	}

	public void setmPriceCoach(double mPriceCoach) {
		if (isValidmPriceFirst(mPriceCoach))
			this.mPriceCoach = mPriceCoach;
		else
			throw new IllegalArgumentException(Double.toString(mPriceCoach));			
	}

	public int getmSeatsCoach() {
		return mSeatsCoach;
	}

	public void setmSeatsCoach(int mSeatsCoach) {
		if (isValidmSeatsFirst (mSeatsCoach))
			this.mSeatsCoach = mSeatsCoach;
		else
			throw new IllegalArgumentException(Integer.toString(mSeatsCoach));
	}
        public List<String> getmSeatTypeAvailable()
        {
            return mSeatTypeAvailable;
        }
        public void setmSeatTypeAvailable(String type)
        {
            if(isValidmSeatTypeAvailable(type))
            {
                mSeatTypeAvailable.add(type);
            }
            else
                throw new IllegalArgumentException(mSeatTypeAvailable.toString());
        }
        public void setmSeatTypeAvailable(List<String> seatTypes)
        {
            for ( String seat: seatTypes)
            {
                if(isValidmSeatTypeAvailable(seat))
                {
                    mSeatTypeAvailable.add(seat);
                }
                else
                    throw new IllegalArgumentException(mSeatTypeAvailable.toString());
            }
          
        }

        public void replacemSeatTypeAvailable(List<String> seatTypes)
        {
            mSeatTypeAvailable = new ArrayList<>(seatTypes);
        }
        
	/**
	 * Compare two airports based on 3 character code
	 * 
	 * This implementation delegates to the case insensitive version of string
	 * compareTo
	 * 
	 * @return results of String.compareToIgnoreCase
	 */
//	public int compareTo(Flight other) {
//		return this.mCode.compareToIgnoreCase(other.mCode);
//	}

	/**
	 * Compare two airports for sorting, ordering
	 * 
	 * Delegates to airport1.compareTo for ordering by 3 character code
	 * 
	 * @param airport1
	 *            the first airport for comparison
	 * @param airport2
	 *            the second / other airport for comparison
	 * @return -1 if airport ordered before airport2, +1 of airport1 after airport2,
	 *         zero if no different in order
	 */
//	public int compare(Flight airport1, Flight airport2) {
//		return airport1.compareTo(airport2);
//	}

	/**
	 * Determine if two airport objects are the same airport
	 * 
	 * Compare another object to this airport and return true if the other object
	 * specifies the same airport as this object
	 * 
	 * @param obj
	 *            is the object to compare against this object
	 * @return true if the param is the same airport as this, else false
	 */
//	@Override
//	public boolean equals(Object obj) {
//		// every object is equal to itself
//		if (obj == this)
//			return true;
//
//		// null not equal to anything
//		if (obj == null)
//			return false;
//
//		// can't be equal if obj is not an instance of Airport
//		if (!(obj instanceof Flight))
//			return false;
//
//		// if all fields are equal, the Airports are the same
//		Flight rhs = (Flight) obj;
//		if ((rhs.mName.equals(mName)) && (rhs.mCode.equals(mCode)) && (rhs.mLatitude == mLatitude)
//				&& (rhs.mLongitude == mLongitude)) {
//			return true;
//		}
//
//		return false;
//	}

	/**
	 * Determine if object instance has valid attribute data
	 * 
	 * Verifies the name is not null and not an empty string. Verifies code is 3
	 * characters in length. Verifies latitude is between +90.0 north pole and -90.0
	 * south pole. Verifies longitude is between +180.0 east prime meridian and
	 * -180.0 west prime meridian.
	 * 
	 * @return true if object passes above validation checks
	 * 
//	 */
//	public boolean isValid() {
//
//		// If the name isn't valid, the object isn't valid
//		if ((mName == null) || (mName == ""))
//			return false;
//
//		// If we don't have a 3 character code, object isn't valid
//		if ((mCode == null) || (mCode.length() != 3))
//			return false;
//
//		// Verify latitude and longitude are within range
//		if ((mLatitude > Saps.MAX_LATITUDE) || (mLatitude < Saps.MIN_LATITUDE) || (mLongitude > Saps.MAX_LONGITUDE)
//				|| (mLongitude < Saps.MIN_LONGITUDE)) {
//			return false;
//		}
//
//		return true;
//	}
//	


	/**
	 * Check for invalid Airplane
	 **/
	public boolean isValidmAirplane(String mAirplane) {
		if(mAirplane == null)
			return false;
		return true;
		
	}
	
	public boolean isValidmFlightTime(int mFlightTime) {
		if(mFlightTime < 0 /* || mFlightTime > 60 */)
			return false;
		return true;
	}
	
	public boolean isValidmNumber(String mNumber) {
		if((mNumber == null) || (mNumber.length() < 4) || (mNumber.length() > 5))
			return false;
		return true;
	}
	
	public boolean isValidmDepAirport(String mDepAirport) {
		if ((mDepAirport == null) || (mDepAirport.length() != 3))
			return false;
		return true;
	}
	
        // TODO need to further refine the date
	public boolean isValidmDepTime(LocalDateTime mDepTime) {
		if ((mDepTime == null) ||
				(mDepTime.getYear() < Saps.MIN_YEAR) ||
				(mDepTime.getYear() > Saps.MAX_YEAR) /* ||
				(mDepTime.getMonthValue() < Saps.MIN_MONTH) ||
				(mDepTime.getMonthValue() > Saps.MAX_MONTH) ||
				(mDepTime.getDayOfMonth() > Saps.MIN_DAY) ||
				(mDepTime.getDayOfMonth() < Saps.MAX_DAY) ||
				(mDepTime.getHour() < Saps.MIN_HOUR) ||
				(mDepTime.getHour() > Saps.MAX_HOUR) ||
				(mDepTime.getMinute() < Saps.MIN_MINUTE) ||
				(mDepTime.getMinute() > Saps.MAX_MINUTE) */
				)
			return false;
		return true;
	}
	
	public boolean isValidmPriceFirst(double mPriceFirst) {
		if ((mPriceFirst < Saps.MIN_PRICE) || (mPriceFirst > Double.MAX_VALUE)) 
			return false;
		return true;
	}
	
	public boolean isValidmSeatsFirst(int mSeatsFirst) {
		if ((mSeatsFirst < Saps.MIN_SEAT) || (mSeatsFirst > Saps.MAX_SEAT))
			return false;
		return true;
	}
        public boolean isValidmSeatTypeAvailable(String type){
            if ( type.equalsIgnoreCase(Saps.COACH_TYPE) || type.equalsIgnoreCase(Saps.FIRST_CLASS_TYPE)) 
			return true;
		return false;
        }

	@Override
	public int compare(Flight o1, Flight o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Flight o) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
