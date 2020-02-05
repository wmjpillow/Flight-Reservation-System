
package flightsystem;

/**
 * Class responsible for creating a ComboBox item
 * ComboBox used to display available airport 
 * @author dg71532
 */
public class ComboItem
{
    private String airportCode;
    private String airportName;
    
    /**
     * Constructor
     * @param code Aiport Code
     * @param name Name of the aiport
     */
    public ComboItem(String code, String name)
    {
        this.airportCode = code;
        this.airportName = name;
    }

    ComboItem() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String toString()
    {
        return airportCode+"-"+airportName;
    }
    
    public String getAirportCode()
    {
        return airportCode;
    }

    public String getAirportName()
    {
        return airportName;
    }
}