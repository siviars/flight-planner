package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogisticRepositoryFlight extends JpaRepository<Flight, Integer> {

    @Query("SELECT f FROM Flight f " +
            "JOIN Airport a on f.from=a.airport " +
            "AND f.to = a.airport " +
            "WHERE f.from.airport = ?1 " +
            "AND f.to.airport = ?2 " +
            "AND f.departureTime = ?3 ")
    List<Flight> searchFlight(String from, String to, LocalDateTime departureDate);
}
