package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.*;

import java.util.List;

public interface LogisticService {

    void clearFlights();

    Flight addFlight(Flight flight);

    List<Flight> getFlight();

    Flight fetchFlight(Integer id);

    void deleteFlight(Integer id);

    SearchItems searchFlight(FlightsRequest flightsRequest);

    Flight findFlightById(Integer id);

    List<Airport> searchAirports(String search);

}
