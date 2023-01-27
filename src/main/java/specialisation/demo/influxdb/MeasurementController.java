package specialisation.demo.influxdb;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/measurements")
@RestController
public class MeasurementController {

    private final MeasurementRepository influxDb;

    public MeasurementController(MeasurementRepository influxDb) {
        this.influxDb = influxDb;
    }

    @PostMapping
    ResponseEntity<MeasurementEntity> createMeasurements(
        @RequestBody MeasurementRequest request
    ) {
        return ResponseEntity.ok(influxDb.store(request));
    }

    @GetMapping
    ResponseEntity<List<MeasurementEntity>> retrieveMeasurements(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date
    ) {
        return ResponseEntity.ok(influxDb.fetch(date));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteMeasurements(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date
    ) {
        influxDb.delete(date);
        return ResponseEntity.ok().build();
    }
}
