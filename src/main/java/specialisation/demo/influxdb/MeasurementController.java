package specialisation.demo.influxdb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.influxdb.config.InfluxDbConfig;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/measurements")
@ConditionalOnBean(InfluxDbConfig.class)
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
