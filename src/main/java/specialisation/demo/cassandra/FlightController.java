package specialisation.demo.cassandra;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.cassandra.config.CassandraConfig;

@RestController
@RequestMapping("/flights")
@ConditionalOnBean(CassandraConfig.class)
public class FlightController {

    private final FlightRepository repository;

    public FlightController(FlightRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<FlightEntity> createFlight(
        @RequestBody FlightRequest request
    ) {
        return ResponseEntity.ok(repository.store(request));
    }

    @GetMapping
    ResponseEntity<FlightEntity> retrieveFlight(
        @RequestParam String flightNumber
    ) {
        return repository.fetch(flightNumber)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    ResponseEntity<Void> deleteFlight(
        @RequestParam String flightNumber
    ) {
        repository.delete(flightNumber);
        return ResponseEntity.ok().build();
    }
}
