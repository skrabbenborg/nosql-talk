package specialisation.demo.mongodb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.mongodb.config.MongoDbConfig;

import java.time.Month;
import java.time.Year;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@ConditionalOnBean(MongoDbConfig.class)
public class ReservationController {

    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<ReservationEntity> createReservation(
        @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(repository.store(request));
    }

    @GetMapping("/{id}")
    ResponseEntity<ReservationEntity> retrieveReservation(
        @PathVariable UUID id
    ) {
        return ResponseEntity.ok(repository.fetchReservation(id));
    }

    @GetMapping("/{year}/{month}")
    ResponseEntity<MonthlyOverviewEntity> retrieveMonthlyOverview(
        @PathVariable Month month,
        @PathVariable Year year
    ) {
        return ResponseEntity.ok(repository.fetchOverview(month, year));
    }
}

