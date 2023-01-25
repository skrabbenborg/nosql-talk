package specialisation.demo.mongodb;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/invoice")
@RestController
public class InvoiceController {

    private final InvoiceRepository repository;

    public InvoiceController(InvoiceRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    ResponseEntity<InvoiceEntity> createMeasurements(
        @RequestBody InvoiceRequest request
    ) {
        return ResponseEntity.ok(repository.store(request));
    }

    @GetMapping()
    ResponseEntity<Iterable<InvoiceEntity>> retrieveMeasurements(
        @RequestParam String name,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date
    ) {
        return ResponseEntity.ok(repository.fetch(name, date));
    }

    @DeleteMapping()
    ResponseEntity<Void> deleteMeasurements(
        @RequestParam UUID id
    ) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }
}
