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
@ConditionalOnProperty(prefix = "myapp", name = "appmode", havingValue = "database")
public class LogisticServiceFromDB implements LogisticService {

    private final LogisticRepositoryAirport logisticRepositoryAirport;
    private final LogisticRepositoryFlight logisticRepositoryFlight;

    public LogisticServiceFromDB(LogisticRepositoryAirport logisticRepositoryAirport, LogisticRepositoryFlight logisticRepositoryFlight) {
        this.logisticRepositoryFlight = logisticRepositoryFlight;
        this.logisticRepositoryAirport = logisticRepositoryAirport;
    }

    @Override
    public void clearFlights() {
        logisticRepositoryFlight.deleteAll();
    }

    @Override
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
                    logisticRepositoryAirport.save(flight.getFrom());
                    logisticRepositoryAirport.save(flight.getTo());
                    logisticRepositoryFlight.save(flight);
                    return flight;
                }
            }
        }
    }

    @Override
    public List<Flight> getFlight() {
        return logisticRepositoryFlight.findAll();
    }

    @Override
    public Flight fetchFlight(Integer id) {
        Flight returnFlight = logisticRepositoryFlight.findById(id).orElse(null);
        if (returnFlight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return returnFlight;
    }

    @Override
    public synchronized void deleteFlight(Integer id) {
        if (logisticRepositoryFlight.existsById(id)) {
            logisticRepositoryFlight.deleteById(id);
        }
    }

    @Override
    public SearchItems searchFlight(FlightsRequest flightsRequest) {
        if (flightsRequest.getFrom() != null && flightsRequest.getTo() != null && flightsRequest.getDepartureDate() != null) {
            if (flightsRequest.getFrom().equals(flightsRequest.getTo())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                List<Flight> items = logisticRepositoryFlight.findAll().stream().filter(fl -> fl.getFrom().getAirport().equals(flightsRequest.getFrom()) &&
                        fl.getTo().getAirport().equals(flightsRequest.getTo()) &&
                        fl.getDepartureTime().toString().substring(0, 10).equals(flightsRequest.getDepartureDate())).toList();
                return new SearchItems(items, 0, items.size());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Flight findFlightById(Integer id) {
        Flight returnFlights = logisticRepositoryFlight.findById(id).orElse(null);
        if (returnFlights == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return returnFlights;
        }
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return logisticRepositoryFlight.findAll().stream().filter(fl -> fl.getFrom().getAirport().toLowerCase().contains(search.trim().toLowerCase()) ||
                fl.getFrom().getCity().toLowerCase().contains(search.trim().toLowerCase()) ||
                fl.getFrom().getCountry().toLowerCase().contains(search.trim().toLowerCase())).map(Flight::getFrom).toList();
    }

    public boolean checkSameFlight(Flight flight) {
        try {
            return logisticRepositoryFlight.findAll().stream().anyMatch(fl ->
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
