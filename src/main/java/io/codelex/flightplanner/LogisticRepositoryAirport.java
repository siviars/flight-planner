package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticRepositoryAirport extends JpaRepository<Airport, Integer> {

}
