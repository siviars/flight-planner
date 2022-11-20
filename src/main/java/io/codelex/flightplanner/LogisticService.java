package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Airport;
import io.codelex.flightplanner.Objects.Flight;
import io.codelex.flightplanner.Objects.FlightsRequest;
import io.codelex.flightplanner.Objects.SearchItems;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LogisticService {
    LogisticRepository logisticRepository;

    public LogisticService(LogisticRepository logisticRepository) {
        this.logisticRepository = logisticRepository;
    }

    public synchronized Flight addFlight(Flight flight) {
//--------------------------Check null----------------------------------------------------------------
        if (flight.getCarrier() != null && flight.getCarrier() != "" &&
                flight.getDepartureTime() != (null) && !flight.getDepartureTime().equals("") &&
                flight.getArrivalTime() != (null) && !flight.getArrivalTime().equals("") &&
                flight.getFrom() != (null) &&
                flight.getTo() != (null)) {
            if (flight.getFrom().getCity() != (null) && !flight.getFrom().getCity().equals("") &&
                    flight.getFrom().getCountry() != (null) && !flight.getFrom().getCountry().equals("") &&
                    flight.getFrom().getAirport() != (null) && !flight.getFrom().getAirport().equals("") &&
                    flight.getTo().getCity() != (null) && !flight.getTo().getCity().equals("") &&
                    flight.getTo().getCountry() != (null) && !flight.getTo().getCountry().equals("") &&
                    flight.getTo().getAirport() != (null) && !flight.getTo().getAirport().equals("")) {
                //--------------------Check same flight------------------------------
                if (logisticRepository.flights.stream().anyMatch(fl ->
                        fl.getFrom().getAirport().equals(flight.getFrom().getAirport()) &&
                                fl.getFrom().getCountry().equals(flight.getFrom().getCountry()) &&
                                fl.getFrom().getCity().equals(flight.getFrom().getCity()) &&
                                fl.getTo().getAirport().equals(flight.getTo().getAirport()) &&
                                fl.getTo().getCountry().equals(flight.getTo().getCountry()) &&
                                fl.getTo().getCity().equals(flight.getTo().getCity()) &&
                                fl.getDepartureTime().equals(flight.getDepartureTime()) &&
                                fl.getArrivalTime().equals(flight.getArrivalTime()) &&
                                fl.getCarrier().equals(flight.getCarrier()))) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT);
                } else {
                    //----------------Check same airports--------------------------
                    if (flight.getTo().getAirport().toUpperCase().trim().equals(flight.getFrom().getAirport().toUpperCase().trim()) &&
                            flight.getFrom().getCity().toUpperCase().trim().equals(flight.getTo().getCity().toUpperCase().trim()) &&
                            flight.getFrom().getCountry().toUpperCase().trim().equals(flight.getTo().getCountry().toUpperCase().trim())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    } else {
                        //---------------------Check time--------------------------
                        if (LocalDateTime.parse(flight.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                                .isAfter(LocalDateTime.parse(flight.getArrivalTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) ||
                                flight.getDepartureTime().equals(flight.getArrivalTime())) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                        } else {
                            return logisticRepository.addFlight(flight);
                        }
                        //------------------End check time-------------------------
                    }
                    //-------------------End check same airports-------------------
                }
                //-------------------End check same flight--------------------------
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
//--------------------------End check null-------------------------------------------
    }

    public void clearFlights() {
        logisticRepository.clearFlights();
    }

    public Flight fetchFlight(Long id) {
        Flight returnFlight = logisticRepository.fetchFlight(id);
        if (returnFlight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return returnFlight;
    }

    public synchronized void deleteFlight(Long id) {
        logisticRepository.deleteFlight(id);
    }

    public SearchItems searchFlight(FlightsRequest flightsRequest) {
        if (flightsRequest.getFrom() != null && flightsRequest.getTo() != null && flightsRequest.getDepartureDate() != null) {
            if (flightsRequest.getFrom().equals(flightsRequest.getTo())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                return logisticRepository.searchFlight(flightsRequest);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Flight findFlightById(Long id) {
        Flight returnFlights = logisticRepository.findFlightById(id);
        if (returnFlights == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return returnFlights;
        }
    }

    public List<Airport> searchAirports(String search) {
        return logisticRepository.searchAirport(search.trim().toLowerCase());
    }

    public List<Flight> getFlight() {
        return logisticRepository.getFlight();
    }
}
