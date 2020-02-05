/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flight;

/**
 * A class indicating whether flight reservation succeed and error message
 * @author dg71532
 */
public class FlightConfirmation {
    private final Boolean confirmed;
    private final String errorMessage;
    public FlightConfirmation(Boolean confirmed, String error) 
    {
        this.confirmed = confirmed;
        this.errorMessage = error;
    }
    public Boolean getConfirmed() {
        return confirmed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    
}
