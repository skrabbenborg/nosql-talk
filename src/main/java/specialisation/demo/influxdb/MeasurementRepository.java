package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.DeletePredicateRequest;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.dsl.Flux;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.influxdb.config.InfluxDbConfig;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.influxdb.query.dsl.functions.restriction.Restrictions.measurement;

@Slf4j
@Service
@ConditionalOnBean(InfluxDbConfig.class)
public class MeasurementRepository {

    public static final String MEASUREMENT = "measured";

    private final Clock clock;
    private final InfluxDBClient influxDb;

    public MeasurementRepository(Clock clock, InfluxDBClient influxDb) {
        this.clock = clock;
        this.influxDb = influxDb;
    }

    public MeasurementEntity store(MeasurementRequest request) {
        MeasurementEntity entity = MeasurementEntity.builder()
            .time(Instant.now(clock))
            .chalet(request.chalet())
            .temp(request.temp())
            .build();

        influxDb.getWriteApiBlocking().writeMeasurement(WritePrecision.MS, entity);
        log.info("Stored {}", entity);

        return entity;
    }

    public List<MeasurementEntity> fetch(LocalDate date) {
        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = start.plus(1, ChronoUnit.DAYS);

        String query = Flux.from("temperature")
            .range(start, end)
            .filter(measurement().equal(MEASUREMENT))
            .pivot(List.of("_time"), List.of("_field"), "_value")
            .toString();

        return influxDb.getQueryApi().query(query, MeasurementEntity.class);
    }

    public void delete(LocalDate date) {
        OffsetDateTime start = date.atStartOfDay().atZone(clock.getZone()).toOffsetDateTime();
        OffsetDateTime end = start.plus(1, ChronoUnit.DAYS);

        var predicate = new DeletePredicateRequest()
            .start(start)
            .stop(end)
            .predicate("_measurement=%s".formatted(MEASUREMENT));

        influxDb.getDeleteApi().delete(predicate, "temperature", "org");
    }
}
