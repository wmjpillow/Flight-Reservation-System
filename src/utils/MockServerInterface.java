/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import airplane.Airplane;
import airplane.Airplanes;
import airport.Airport;
import airport.Airports;
import dao.ServerInterface.QueryFlightFilter;
import dao.ServerInterface.QueryFlightType;
import flight.Flight;
import flight.Flights;
import flight.ReserveFlight;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Mock server for testing
 * @author dg71532
 */
public class MockServerInterface implements Server {
    private boolean lockState = false;
    private Airports airports = null;
    private HashMap<String, Flight> flightMap = new HashMap<>();
    private Airplanes airplanes = new Airplanes();

    @Override
    public Airports getAirports (String teamName)
    {
        System.out.println("getAirports");
        Airport boston = new Airport("Boston Airport", "BOS", 0, 10);
        Airports airports = new Airports();
        airports.add(boston);
        return airports;
    }
    @Override
    public Airplanes getAirplanes(String teamName)
    {
        System.out.println(" getAirplanes");
        return airplanes;
    }

    public void  setAirPort(Airports ap)
    {
    	System.out.println(" setAirplanes");
        airports = ap;
    }

    @Override
    public Flights getFlights(String teamName, QueryFlightType type, String airportCode, LocalDateTime gmtDateTime, QueryFlightFilter filter)
    {
        System.out.println(" getFlights");
        Flights flights = new Flights();
        flights.add(flightMap.get(airportCode));
        return flights;
    }
    
    public void setFlight(Flight flight)
    {
    	System.out.println(" setFlight");
    	flightMap.put(flight.getmDepAirport(), flight);
    		
    }
    public void setAirplane(Airplane airplane)
    {
        airplanes.add(airplane);
    }
    @Override
    public boolean lock (String teamName)
    {
    	System.out.println(" lock");
    	lockState = true;
        return lockState;
    }
    @Override
    public boolean unlock (String teamName)
    {
        System.out.println(" unlock");
        lockState = false;
        return lockState;
    }
    @Override
    public boolean reserveSeat(String teamName, ReserveFlight reserveFlightObj)
    {
        System.out.println(" reserveSeat");
        return false;
    }
}
