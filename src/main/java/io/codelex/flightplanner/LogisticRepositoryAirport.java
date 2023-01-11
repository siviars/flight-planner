package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticRepositoryAirport extends JpaRepository<Airport, Integer> {

    @Query("SELECT a FROM Airport a " +
            "WHERE LOWER(a.airport) LIKE ?1% " +
            "OR LOWER(a.city) LIKE ?1% " +
            "OR LOWER(a.country) LIKE ?1% ")
    List<Airport> searchAirports(String search);

}
