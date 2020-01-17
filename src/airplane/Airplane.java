package airplane;

import java.util.Comparator;

/**
 * Airplane class describing airplane model, manufacturer and seat capacity
 * @author alvin
 */
public class Airplane implements Comparable<Airplane>, Comparator<Airplane> {
    
    public static String COACH = "Coach";
    public static String FIRST = "FirstClass";
    
	private String mManufacturer;
	private String mModel; // airplane model name
	private int mFirstClassSeats; // seat capacity of first class
	private int mCoachSeats; // seat capacity of coach class

	public Airplane() {
		mManufacturer = "";
		mModel = "";
		mFirstClassSeats = Integer.MAX_VALUE;
		mCoachSeats = Integer.MAX_VALUE;
	}

	public Airplane(String manufacturer, String model, int firstClassSeats, int coachSeats) {
		if (!isValidString(manufacturer))
			throw new IllegalArgumentException(manufacturer);
		if (!isValidString(model))
			throw new IllegalArgumentException(model);
		if (!isValidSeats(firstClassSeats))
			throw new IllegalArgumentException(Integer.toString(firstClassSeats));
		if (!isValidSeats(coachSeats))
			throw new IllegalArgumentException(Integer.toString(coachSeats));

		mManufacturer = manufacturer;
		mModel = model;
		mFirstClassSeats = firstClassSeats;
		mCoachSeats = coachSeats;
	}

	public String getmManufacturer() {
		return mManufacturer;
	}

	public void setmManufacturer(String mManufacturer) {
		if (isValidString(mManufacturer))
			this.mManufacturer = mManufacturer;
		else
			throw new IllegalArgumentException(mManufacturer);
	}

	public String getmModel() {
		return mModel;
	}

	public void setmModel(String mModel) {
		if (isValidString(mModel))
			this.mModel = mModel;
		else
			throw new IllegalArgumentException(mModel);
	}

	public int getmFirstClassSeats() {
		return mFirstClassSeats;
	}

	public void setmFirstClassSeats(int mFirstClassSeats) {
		if (isValidSeats(mFirstClassSeats))
			this.mFirstClassSeats = mFirstClassSeats;
		else
			throw new IllegalArgumentException(Integer.toString(mFirstClassSeats));
	}

	public int getmCoachSeats() {
		return mCoachSeats;
	}

	public void setmCoachSeats(int mCoachSeats) {
		if (isValidSeats(mCoachSeats))
			this.mCoachSeats = mCoachSeats;
		else
			throw new IllegalArgumentException(Integer.toString(mCoachSeats));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(mManufacturer).append(", ");
		sb.append(mModel).append(", ");
		sb.append(mFirstClassSeats).append(", ");
		sb.append(mCoachSeats);

		return sb.toString();
	}
	
	@Override
	public int compare(Airplane o1, Airplane o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}

	@Override
	public int compareTo(Airplane o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isValid() {
		return isValidString(mManufacturer) && isValidString(mModel) && isValidSeats(mFirstClassSeats)
				&& isValidSeats(mCoachSeats);
	}

	public boolean isValidString(String str) {
		if (str == null || str.length() == 0)
			return false;
		return true;
	}

	public boolean isValidSeats(int seats) {
		if (seats < 0 || seats > Integer.MAX_VALUE)
			return false;
		return true;
	}
}
