package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Airport;
import io.codelex.flightplanner.Objects.Flight;
import io.codelex.flightplanner.Objects.FlightsRequest;
import io.codelex.flightplanner.Objects.SearchItems;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LogisticRepository {
    private Long idNum = 1L;
    List<Flight> flights = new ArrayList<>();

    public Flight addFlight(Flight flight) {
        flight.setId(idNum);
        flights.add(flight);
        idNum++;
        return flight;
    }

    public void clearFlights() {
        flights.clear();
        idNum = 1L;
    }

    public Flight fetchFlight(Long id) {
        return flights.stream().filter(myid -> id.equals(myid.getId())).findAny().orElse(null);
    }

    public List<Flight> getFlight() {
        return flights;
    }

    public void deleteFlight(Long id) {
        flights.remove(flights.stream().filter(myid -> id.equals(myid.getId())).findAny().orElse(null));
    }

    public SearchItems searchFlight(FlightsRequest flightsRequest) {
        List<Flight> items = flights.stream().filter(fl -> fl.getFrom().getAirport().equals(flightsRequest.getFrom()) &&
                fl.getTo().getAirport().equals(flightsRequest.getTo()) &&
                fl.getDepartureTime().substring(0, 10).equals(flightsRequest.getDepartureDate())).toList();
        return new SearchItems(items, 0, items.size());
    }

    public Flight findFlightById(Long id) {
        return flights.stream().filter(myid -> id.equals(myid.getId())).findAny().orElse(null);
    }

    public List<Airport> searchAirport(String search) {
        return flights.stream().filter(fl -> fl.getFrom().getAirport().toLowerCase().equals(search) ||
                fl.getFrom().getCity().toLowerCase().equals(search) ||
                fl.getFrom().getCountry().toLowerCase().equals(search)).map(Flight::getFrom).toList();
    }
}
