/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import airplane.Airplanes;
import airport.Airports;
import dao.ServerInterface;
import flight.Flights;
import flight.ReserveFlight;
import java.time.LocalDateTime;

/**
 * Mock server and the actual server implement this interface.
 * @author dg71532
 */
public interface Server {
    public Airports getAirports (String teamName);
    public Airplanes getAirplanes(String teamName);
    public Flights getFlights(String teamName, ServerInterface.QueryFlightType type, String airportCode, LocalDateTime gmtDateTime, ServerInterface.QueryFlightFilter filter);
    public boolean lock (String teamName);
    public boolean unlock (String teamName);
    public boolean reserveSeat(String teamName, ReserveFlight reserveFlightObj);
}
