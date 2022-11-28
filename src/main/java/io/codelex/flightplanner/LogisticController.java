package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Airport;
import io.codelex.flightplanner.Objects.Flight;
import io.codelex.flightplanner.Objects.FlightsRequest;
import io.codelex.flightplanner.Objects.SearchItems;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class LogisticController {
    private LogisticService logisticService;

    public LogisticController(LogisticService logisticService) {
        this.logisticService = logisticService;
    }

    @PostMapping("/testing-api/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearFlights() {
        logisticService.clearFlights();
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody @Valid Flight flight) {
        return logisticService.addFlight(flight);
    }

    @GetMapping("/admin-api/flights")
    public List<Flight> getFlight() {
        return logisticService.getFlight();
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight fetchFlight(@PathVariable("id") Long id) {
        return logisticService.fetchFlight(id);
    }

    @DeleteMapping("/admin-api/flights/{id}")
    public void deleteFlight(@PathVariable("id") Long id) {
        logisticService.deleteFlight(id);
    }

    @PostMapping("/api/flights/search")
    public SearchItems searchFlight(@RequestBody FlightsRequest flightsRequest) {
        return logisticService.searchFlight(flightsRequest);
    }

    @GetMapping("/api/flights/{id}")
    public Flight findFlightById(@PathVariable("id") Long id) {
        return logisticService.findFlightById(id);
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return logisticService.searchAirports(search);
    }

}
