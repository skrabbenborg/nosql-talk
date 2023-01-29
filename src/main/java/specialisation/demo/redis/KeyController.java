package specialisation.demo.redis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/keys")
@RestController
public class KeyController {

    private final KeyRepository repository;

    public KeyController(KeyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<Boolean> retrieveAvailability(
        @RequestParam Integer chaletNumber
    ) {
        Boolean available = repository.get(chaletNumber);
        return ResponseEntity.ok(available);
    }

    @PostMapping
    ResponseEntity<Void> setAvailability(
        @RequestParam Integer chaletNumber,
        @RequestParam Boolean available
    ) {
        repository.set(chaletNumber, available);
        return ResponseEntity.ok().build();
    }
}
