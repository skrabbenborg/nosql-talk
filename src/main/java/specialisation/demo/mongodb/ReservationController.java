package specialisation.demo.mongodb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.mongodb.config.MongoDbConfig;

import java.util.List;
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
    ResponseEntity<List<ReservationEntity>> retrieveReservation(
        @PathVariable UUID id
    ) {
        return ResponseEntity.ok(repository.fetch(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> addGuestToReservation(
        @PathVariable UUID id,
        @RequestParam String name,
        @RequestParam Integer age
    ) {
        return repository.addGuest(id, name, age) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/projection-by-name")
    ResponseEntity<List<ReservationEntity>> retrieveProjectionOfGuestsByName(
        @RequestParam String name
    ) {
        return ResponseEntity.ok(repository.fetchByGuestName(name));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteReservation(
        @RequestParam UUID id
    ) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }
}

