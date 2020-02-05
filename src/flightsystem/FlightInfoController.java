/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightsystem;

import airplane.Airplane;
import airplane.Airplanes;
import airport.Airport;
import airport.AirportZoneMap;
import airport.Airports;
import airport.TimeConverter;
import dao.ServerInterface;
import flight.Flight;
import flight.FlightConfirmation;
import flight.Flights;
import flight.ReserveFlight;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import utils.Server;

/**
 * Controller class standing between UI and server interface. It implements major 
 * business logic of this system.
 * @author alvin
 */
public class FlightInfoController {

    private static final Object serverLck = new Object();
    private static Server serverInstance;
    
    /**
     * Switch controller's server backend to another
     * @param server 
     */
    public static void SwitchServer(Server server) {
        serverInstance = server;
    }
    
    // TODO shouldn't be here
    private static final String teamName = "CS509team1";
    private static final Logger controllerLogger;

    static {
        controllerLogger = Logger.getLogger(FlightInfoController.class.getName());
        controllerLogger.setLevel(Level.FINE);
    }

    private Flights directFlightsCache;
    private Airports airportsCache;
    private Map<String, Airplane> airplaneCache;

    public FlightInfoController() {
        if ( serverInstance == null )
        {
            serverInstance = ServerInterface.INSTANCE;
        }
        getAirplanes();
    }

    /**
     * Callback object for searchDirectFlight
     */
    public interface FlightsReceiver {
        public void onReceived(Flights ret);
    }
    
    /**
     * Callback object for reserveFlight
     */
    public interface FlightConfirmationReceiver
    {
        public void onReceived(FlightConfirmation confirm);
    }
    
    /**
     * Callback object for searchStopoverFlights
     */
    public interface StopoverFlightsReceiver {
        public void onReceived(List<List<Flight>> ret);
    }

    /**
     * Get airport object
     * @return airports 
     */
    public Airports getAirports() {
        synchronized (serverLck) {
            airportsCache = serverInstance.getAirports(teamName);
        }
        return airportsCache;
    }
    
    /**
     * Acquire the lock, reserve the flights, and unlock the database.
     * If the request can't acquire the lock in 30 seconds, then it will fail.
     * @param reserveFlightObj - the flights that need to be reserved
     * @param receiver
     */
    public void reserveFlight(ReserveFlight reserveFlightObj, FlightConfirmationReceiver receiver)
    {
        String xml = reserveFlightObj.getXML();
        if ( xml.isEmpty() ){
            if( receiver != null)
            {
                 receiver.onReceived(new FlightConfirmation(false, "Received an empty list of flights"));
            }
        }
        else
        {
            final FlightConfirmation flightConfirm = new FlightConfirmation(false, "Couldn't reserve a flight, experiencing issues with database. Please try again or select a different flight.");
            Timer timer = new Timer();
            AtomicBoolean timeOut = new AtomicBoolean();
            timeOut.set(false);
            timer.schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    timeOut.set(true);
                    Runnable _r = () -> receiver.onReceived(flightConfirm);
                    SwingUtilities.invokeLater(_r);
                }

            }, 30000);
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    boolean isReserved = false;
                    synchronized (serverLck) {
                        boolean isGetLock = serverInstance.lock(teamName);
                        while (!isGetLock) {
                            isGetLock = serverInstance.lock(teamName);
                            if (!isGetLock) {
                                try {
                                    Thread.sleep(200L);
                                } catch (InterruptedException ex) {
                                    controllerLogger.log(Level.SEVERE, ex.toString());
                                }
                            }
                            if (timeOut.get() == true) {
                                if (isGetLock) {
                                    serverInstance.unlock(teamName);
                                }
                                return;
                            }
                        }
                        timer.cancel();
                        isReserved = serverInstance.reserveSeat(teamName, reserveFlightObj);
                        serverInstance.unlock(teamName);
                    }
                    FlightConfirmation flightConfirm;
                    if (isReserved) {
                        flightConfirm = new FlightConfirmation(true, "");
                    } else {
                        flightConfirm = new FlightConfirmation(false, "Cannot reserve flight");
                    }
                    Runnable _r = () -> receiver.onReceived(flightConfirm);
                    SwingUtilities.invokeLater(_r);
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
        
        
    }
    
    /**
     * search for direct flights
     * @param fromAirportCode
     * @param fromTime
     * @param toAirportCode
     * @param seatTypes
     * @param receiver 
     */
    public void searchDirectFlight(String fromAirportCode, LocalDateTime fromTime,
            String toAirportCode, List<String> seatTypes, FlightsReceiver receiver) {
        if (airportsCache == null) {
            getAirports();
        }
        if (fromAirportCode == null || fromTime == null || toAirportCode == null
                || receiver == null) {
            controllerLogger.log(Level.SEVERE, "searchDirectFlight args error");
            receiver.onReceived(new Flights());
            return;
        }
        final Level logLevel = Level.INFO;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Flights ret;
                synchronized (serverLck) {
                    ret = searchFlightsImpl(fromAirportCode, fromTime, toAirportCode, seatTypes);
                    controllerLogger.log(logLevel, "flight count={0}", ret.size());
                    directFlightsCache = ret;
                }
                Runnable _r = () -> receiver.onReceived(ret);
                SwingUtilities.invokeLater(_r);
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    
    /**
     * Search for Flights with stopovers
     * @param depAirportCode - Departure airport code
     * @param depTime - Departure time
     * @param arrAirportCode - Arrival airport code
     * @param seatTypes - Seat type that is being reserved
     * @param stopover - The stop over that is required by user
     * @param receiver 
     */
    public void searchStopoverFlights(String depAirportCode, LocalDateTime depTime, 
            String arrAirportCode, List<String> seatTypes, int stopover, 
            StopoverFlightsReceiver receiver) {
        if (depAirportCode == null || depTime == null || arrAirportCode == null ||
                seatTypes == null) {
            controllerLogger.log(Level.SEVERE, "SearchStopoverFlights args error");
            List<List<Flight>> ret = new ArrayList<>();
            ret.add(new ArrayList<>());
            receiver.onReceived(ret);
            return;
        }
        final Level logLevel = Level.INFO;
        Runnable r = () -> {
            List<List<Flight>> ret;
            synchronized (serverLck) {
                ret = searchStopoverFlightsImpl(depAirportCode, depTime, 
                        arrAirportCode, seatTypes, stopover);
            }
            controllerLogger.log(logLevel, "flight count={0}", ret.size());
            Runnable _r = () -> receiver.onReceived(ret);
            SwingUtilities.invokeLater(_r);
        };
        Thread t = new Thread(r);
        t.start();
    }
    
    /**
     * Testing the searching for stopover flights
     * @param trial 
     */
    private void stopOverSearchTest(List<List<Flight>> trial) {
        Level level = Level.INFO;
        controllerLogger.log(level, "trialSize={0}", trial.size());
        for (List<Flight> sublist : trial) {
            StringBuilder builder = new StringBuilder();
            for (Flight f : sublist) {
                String depCode = f.getmDepAirport();
                LocalDateTime depDateTime = f.getmDepTime();
                String arrCode = f.getmArrAirport();
                LocalDateTime arrDateTime = f.getmArrTime();
                String msg = String.format("depCode=%s depDateTime=%s arrCode=%s arrDateTime=%s",
                        depCode, depDateTime.toString(), arrCode, arrDateTime.toString());
                controllerLogger.log(level, msg);
                builder.append(f.getmNumber());
                builder.append(", ");
            }
            controllerLogger.log(level, builder.toString());
        }
    }

    /**
     * Check if the flight is exist in the previous stop over flights search history
     * @param list - flights which are added in the list
     * @param flight - the flight that needs to be checked
     * @return true if the flight already exists in the previous search, otherwise, return false 
     */
    private boolean isInDfsHistory(List<Flight> list, Flight flight) {
        Level level = Level.INFO;
        controllerLogger.log(level, "===START===");
        StringBuilder builder = new StringBuilder();
        for (Flight f : list) {
            String depCode = f.getmDepAirport();
            LocalDateTime depDateTime = f.getmDepTime();
            String arrCode = f.getmArrAirport();
            LocalDateTime arrDateTime = f.getmArrTime();
            String msg = String.format("depCode=%s depDateTime=%s arrCode=%s arrDateTime=%s",
                    depCode, depDateTime.toString(), arrCode, arrDateTime.toString());
            controllerLogger.log(level, msg);
            builder.append(f.getmNumber());
            builder.append(", ");
            if (flight.getmArrAirport().equals(f.getmDepAirport())) {
                return true;
            }
        }
        controllerLogger.log(level, builder.toString());
        controllerLogger.log(level, "===END===");
        return false;
    }

    /**
     * Calculate the duration between two flights
     * @param t1 - start time
     * @param t2 - end time
     * @return the difference between two time
     */
    private static long DurationMins(LocalDateTime t1, LocalDateTime t2) {
        return Duration.between(t1, t2).toMinutes();
    }
    
    private List<List<Flight>> _stopoverFlights;
    
    /**
     * Check if the flight has available seats for the seat type
     * @param f - flight that is being checked
     * @param seatTypes - a list seat types to be checked
     * @return true is seat type is available, otherwise return false
     */
    private boolean isAnySeatTypeAvailable(Flight f, List<String> seatTypes) {
        boolean avail = false;
        for (String seatType : seatTypes) {
            if (isSeatAvailable(f, airplaneCache.get(f.getmAirplane()), seatType)) {
                avail = true;
                break;
            }
        }
        return avail;
    }
    
    /**
     * Search for the first level of flight for searching stop over flights
     * @param fromAirportCode - Departure airport code
     * @param fromTime - Departure time
     * @param toAirportCode - Arrival airport code
     * @param seatTypes - Seat type that is being reserved
     * @param stopover - The stop over that is required by user
     * @return a list of a list of flights
     */
    private List<List<Flight>> searchStopoverFlightsImpl(String depAirportCode, 
            LocalDateTime depTime, String arrAirportCode, List<String> seatTypes, int stopover) {
        Airport depAirport = getAirportByCode(depAirportCode);
        ZoneId depZoneId = AirportZoneMap.GetTimeZoneByAiport(depAirport);
        ZonedDateTime zonedDepDateTime = ZonedDateTime.of(depTime, depZoneId);
        LocalDateTime gmtDepDateTime = zonedDepDateTime.withZoneSameInstant(
                    ZoneId.of("GMT")).toLocalDateTime();
        ServerInterface.QueryFlightFilter lv1Filter = new ServerInterface.QueryFlightFilter() {
            @Override
            public boolean isValid(Flight f) {
                if (f == null) return false;
                if (!isAnySeatTypeAvailable(f, seatTypes)) {
                    return false;

                }
                if (f.getmDepTime().isAfter(gmtDepDateTime)) {
                    f.setmSeatTypeAvailable(seatTypes);
                    return true;
                } else {
                    return false;
                }
            }
        };
        _stopoverFlights = new ArrayList<>();
        Flights flights = serverInstance.getFlights(teamName, ServerInterface.QueryFlightType.DEPART, 
                depAirportCode, gmtDepDateTime, lv1Filter);
        int level = 1;
        for (Flight f: flights) {
            String nextDepAirportCode = f.getmArrAirport();
            LocalDateTime nextDepDateTime = f.getmArrTime();
            List<Flight> history = new ArrayList<>();
            history.add(f);
            searchStopoverFlightsInner(history, nextDepAirportCode, nextDepDateTime, 
                    arrAirportCode, seatTypes, level + 1, stopover);
        }

        return _stopoverFlights;
    }
    
    /**
     * The search algorithm for after second levels of tree
     * @param history - previous flight
     * @param fromAirportCode - Departure airport code
     * @param fromTime - Departure time
     * @param toAirportCode - Arrival airport code
     * @param seatTypes - Seat type that is being reserved
     * @param level - The current level we are currently searching
     * @param stopover - The stop over that is required by user
     */
    private void searchStopoverFlightsInner(List<Flight> history, 
            String depAirportCode, LocalDateTime depTime, String arrAirportCode, 
            List<String> seatTypes, int level, int stopover) {
        ServerInterface.QueryFlightFilter highLvFilter = new ServerInterface.QueryFlightFilter() {
            @Override
            public boolean isValid(Flight f) {
                if (f == null) return false;
                if (!isAnySeatTypeAvailable(f, seatTypes)) {
                    return false;

                }
                long diff = DurationMins(depTime, f.getmDepTime());
                if ((diff >= 30 && diff <= 240) && !isInDfsHistory(history, f)) {
                    f.setmSeatTypeAvailable(seatTypes);
                    return true;
                } else {
                    return false;
                }
            }
        };
        Flights flights = serverInstance.getFlights(teamName, ServerInterface.QueryFlightType.DEPART, 
                depAirportCode, depTime, highLvFilter);
        for (Flight f: flights) {
            List<Flight> newHistory = new ArrayList<>(history);
            newHistory.add(f);
            if (newHistory.size() == stopover+1) {
                if (f.getmArrAirport().equals(arrAirportCode)) {
                    convertToAirportTime(newHistory);
                    _stopoverFlights.add(newHistory);
                }
            } else {
                String nextDepAirportCode = f.getmArrAirport();
                LocalDateTime nextDepDateTime = f.getmArrTime();
                searchStopoverFlightsInner(newHistory, nextDepAirportCode, nextDepDateTime, 
                    arrAirportCode, seatTypes, level + 1, stopover);
            }
        }
    }

    /**
     * Search for the direct flight
     * @param fromAirportCode - Departure airport code
     * @param fromTime - Departure time
     * @param toAirportCode - Arrival airport code
     * @param seatTypes - Seat type that is being reserved
     * @return all the flights list
     */
    private Flights searchFlightsImpl(String fromAirportCode, LocalDateTime fromTime,
            String toAirportCode, List<String> seatTypes) {
        final Airport fromAirport = getAirportByCode(fromAirportCode);
        ZoneId fromZoneId = AirportZoneMap.GetTimeZoneByAiport(fromAirport);
        final ZonedDateTime zonedFromDateTime = ZonedDateTime.of(fromTime, fromZoneId);
        final LocalDateTime gmtFromDateTime = zonedFromDateTime.withZoneSameInstant(
                ZoneId.of("GMT")).toLocalDateTime();
        final ServerInterface.QueryFlightFilter arrivalFilter = (Flight f) -> {
            if (f == null) {
                return false;
            }
            if (!isAnySeatTypeAvailable(f, seatTypes)) {
                return false;
            }
            if (f.getmArrAirport().equals(toAirportCode)) {
                if (f.getmDepTime().isAfter(gmtFromDateTime)) {
                    f.setmSeatTypeAvailable(seatTypes);
                    return true;
                }
            }
            return false;
        };
        Flights ret = serverInstance.getFlights(teamName, ServerInterface.QueryFlightType.DEPART, fromAirport.code(),
                gmtFromDateTime, arrivalFilter);
        convertToAirportTime(ret);
        return ret;
    }
    
    /**
     * Get the airport by airport code
     * @param code - airport code
     * @return Airport object
     */
    private Airport getAirportByCode(String code) {
        if (airportsCache == null) {
            return null;
        }
        Airport ret = null;
        for (Airport a : airportsCache) {
            if (a.code().equals(code)) {
                ret = a;
                break;
            }
        }
        return ret;
    }
    
    /**
     * Check if the seat type of the flight has seat left
     * @param flight - the flight is going to be reserved
     * @param airplane - the airplane that needs to be checked
     * @param seattype - seat type is going to be reserved
     * @return true is the seat is available, otherwise return false;
     */
    private boolean isSeatAvailable(Flight flight, Airplane airplane, String seattype) {
        boolean availableSeats = false;

        if (seattype.equalsIgnoreCase(Airplane.COACH)) {
            int mSeatsCoach = flight.getmSeatsCoach();
            int mCoachSeats = airplane.getmCoachSeats();
            int remCoachSeats = mCoachSeats - mSeatsCoach;
            if (remCoachSeats > 0) {
                availableSeats = true;
            }

        } else if (seattype.equalsIgnoreCase(Airplane.FIRST)) {
            int mSeatsFirst = flight.getmSeatsFirst();
            int mFirstClassSeats = airplane.getmFirstClassSeats();
            int remFirstClassSeats = mFirstClassSeats - mSeatsFirst;
            if (remFirstClassSeats > 0) {
                availableSeats = true;
            }
        }

        return availableSeats;

    }
    
    /**
     * Get all the airplanes from database
     */
    public void getAirplanes() {
        Airplanes allPlanes = serverInstance.getAirplanes(teamName);
        airplaneCache = new HashMap<>();
        for (Airplane a: allPlanes) {
            String model = a.getmModel();
            airplaneCache.put(model, a);
        }
    }
        
    /**
     * Convert the departure and arrival time to the airport local time
     * @param flights - the flights need to be converted
     */
    public void convertToAirportTime(List<Flight> flights) {
        String mDepAirport;
        LocalDateTime mDepTime;
        String mArrAirport;
        LocalDateTime mArrTime;

        for (Flight flight : flights) {
            mDepAirport = flight.getmDepAirport();
            mDepTime = flight.getmDepTime();
            mArrAirport = flight.getmArrAirport();
            mArrTime = flight.getmArrTime();

            //Airportcode -> Airport
            Airport depAirport = getAirportByCode(mDepAirport);
            Airport arrAirport = getAirportByCode(mArrAirport);

            ZoneId zoneDep = AirportZoneMap.GetTimeZoneByAiport(depAirport);
            ZoneId zoneArr = AirportZoneMap.GetTimeZoneByAiport(arrAirport);
            ZoneId gmtZone = ZoneId.of("GMT");

            ZonedDateTime gmtDepTime = ZonedDateTime.of(mDepTime, gmtZone);
            flight.setmDepTime(gmtDepTime.withZoneSameInstant(zoneDep).toLocalDateTime());

            ZonedDateTime gmtArrTime = ZonedDateTime.of(mArrTime, gmtZone);
            flight.setmArrTime(gmtArrTime.withZoneSameInstant(zoneArr).toLocalDateTime());
        }
    }
}


