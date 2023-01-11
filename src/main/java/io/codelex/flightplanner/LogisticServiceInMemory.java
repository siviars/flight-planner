package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "myapp", name = "appmode", havingValue = "inmemory")
public class LogisticServiceInMemory implements LogisticService {
    LogisticRepositoryInMemory logisticRepositoryInMemory;

    public LogisticServiceInMemory(LogisticRepositoryInMemory logisticRepositoryInMemory) {
        this.logisticRepositoryInMemory = logisticRepositoryInMemory;
    }

    public synchronized Flight addFlight(Flight flight) {
        if (checkSameFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            if (checkSameAirport(flight)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                if (checkTime(flight)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                } else {
                    return logisticRepositoryInMemory.addFlight(flight);
                }
            }
        }
    }

    public void clearFlights() {
        logisticRepositoryInMemory.clearFlights();
    }

    public Flight fetchFlight(Integer id) {
        Flight returnFlight = logisticRepositoryInMemory.fetchFlight(id);
        if (returnFlight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return returnFlight;
    }

    public synchronized void deleteFlight(Integer id) {
        logisticRepositoryInMemory.deleteFlight(id);
    }

    public SearchItems searchFlight(FlightsRequest flightsRequest) {
        if (flightsRequest.getFrom() != null && flightsRequest.getTo() != null && flightsRequest.getDepartureDate() != null) {
            if (flightsRequest.getFrom().equals(flightsRequest.getTo())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                return logisticRepositoryInMemory.searchFlight(flightsRequest);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Flight findFlightById(Integer id) {
        Flight returnFlights = logisticRepositoryInMemory.findFlightById(id);
        if (returnFlights == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return returnFlights;
        }
    }

    public List<Airport> searchAirports(String search) {
        return logisticRepositoryInMemory.searchAirport(search.trim().toLowerCase());
    }

    public List<Flight> getFlight() {
        return logisticRepositoryInMemory.getFlight();
    }


    public boolean checkSameFlight(Flight flight) {
        try {
            return logisticRepositoryInMemory.getFlight().stream().anyMatch(fl ->
                    fl.getFrom().getAirport().equals(flight.getFrom().getAirport()) &&
                            fl.getFrom().getCountry().equals(flight.getFrom().getCountry()) &&
                            fl.getFrom().getCity().equals(flight.getFrom().getCity()) &&
                            fl.getTo().getAirport().equals(flight.getTo().getAirport()) &&
                            fl.getTo().getCountry().equals(flight.getTo().getCountry()) &&
                            fl.getTo().getCity().equals(flight.getTo().getCity()) &&
                            fl.getDepartureTime().equals(flight.getDepartureTime()) &&
                            fl.getArrivalTime().equals(flight.getArrivalTime()) &&
                            fl.getCarrier().equals(flight.getCarrier()));
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean checkSameAirport(Flight flight) {
        return (flight.getTo().getAirport().toUpperCase().trim().equals(flight.getFrom().getAirport().toUpperCase().trim()) &&
                flight.getFrom().getCity().toUpperCase().trim().equals(flight.getTo().getCity().toUpperCase().trim()) &&
                flight.getFrom().getCountry().toUpperCase().trim().equals(flight.getTo().getCountry().toUpperCase().trim()));

    }

    public boolean checkTime(Flight flight) {
        return (LocalDateTime.parse(flight.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                .isAfter(LocalDateTime.parse(flight.getArrivalTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) ||
                flight.getDepartureTime().equals(flight.getArrivalTime()));
    }
}
