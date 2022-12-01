package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Airport;
import io.codelex.flightplanner.Objects.Flight;
import io.codelex.flightplanner.Objects.FlightsRequest;
import io.codelex.flightplanner.Objects.SearchItems;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LogisticRepositoryInMemory {
    private Integer idNum = 1;
    List<Flight> flights = new ArrayList<>();

    public Flight addFlight(Flight flight) {
        flight.setId(idNum);
        flights.add(flight);
        idNum++;
        return flight;
    }

    public void clearFlights() {
        flights.clear();
        idNum = 1;
    }

    public Flight fetchFlight(Integer id) {
        return flights.stream().filter(myid -> id.equals(myid.getId())).findAny().orElse(null);
    }

    public List<Flight> getFlight() {
        return flights;
    }

    public void deleteFlight(Integer id) {
        flights.removeIf(fly -> fly.getId().equals(id));
    }

    public SearchItems searchFlight(FlightsRequest flightsRequest) {
        List<Flight> items = flights.stream().filter(fl -> fl.getFrom().getAirport().equals(flightsRequest.getFrom()) &&
                fl.getTo().getAirport().equals(flightsRequest.getTo()) &&
                fl.getDepartureTime().toString().substring(0, 10).equals(flightsRequest.getDepartureDate())).toList();
        return new SearchItems(items, 0, items.size());
    }

    public Flight findFlightById(Integer id) {
        return flights.stream().filter(myid -> id.equals(myid.getId())).findAny().orElse(null);
    }

    public List<Airport> searchAirport(String search) {
        return flights.stream().filter(fl -> fl.getFrom().getAirport().toLowerCase().contains(search) ||
                fl.getFrom().getCity().toLowerCase().contains(search) ||
                fl.getFrom().getCountry().toLowerCase().contains(search)).map(Flight::getFrom).toList();
    }
}