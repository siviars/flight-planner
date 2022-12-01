package io.codelex.flightplanner;

import io.codelex.flightplanner.Objects.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticRepositoryFlight extends JpaRepository<Flight, Integer> {
}
