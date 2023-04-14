package specialisation.demo.influxdb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import specialisation.demo.influxdb.config.InfluxDbConfig;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/temp/analysis")
@ConditionalOnBean(InfluxDbConfig.class)
public class TempAnalysisController {

    private final InfluxDbRepository<TempAnalysisEntity> repository;
    private final Clock clock;

    public TempAnalysisController(InfluxDbRepository<TempAnalysisEntity> repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @GetMapping
    ResponseEntity<List<TempAnalysisEntity>> retrieveAnalysis(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<OffsetDateTime> start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<OffsetDateTime> end
    ) {
        return ResponseEntity.ok(repository.fetch(
            start.orElse(OffsetDateTime.now(clock).minusMonths(1)),
            end.orElse(OffsetDateTime.now(clock))
        ));
    }
}
