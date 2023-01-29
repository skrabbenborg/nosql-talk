package specialisation.demo.influxdb;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.BoundParameterQuery.QueryBuilder;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.influxdb.config.InfluxDbConfig;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@ConditionalOnBean(InfluxDbConfig.class)
public class MeasurementRepository {

    private final Clock clock;
    private final InfluxDBMapper influxDb;

    public MeasurementRepository(Clock clock, InfluxDBMapper influxDb) {
        this.clock = clock;
        this.influxDb = influxDb;
    }

    public MeasurementEntity store(MeasurementRequest request) {
        MeasurementEntity entity = MeasurementEntity.builder()
            .time(clock.instant())
            .type(request.chalet())
            .measurement(request.measurement())
            .build();

        influxDb.save(entity);
        log.info("Stored {}", entity);

        return entity;
    }

    public List<MeasurementEntity> fetch(LocalDate date) {
        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = start.plus(1, ChronoUnit.DAYS);

        Query query = QueryBuilder.newQuery("SELECT * FROM temperatures WHERE time > $start AND time < $end")
            .bind("start", start)
            .bind("end", end)
            .create();

        return influxDb.query(query, MeasurementEntity.class);
    }

    public void delete(LocalDate date) {
        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = start.plus(1, ChronoUnit.DAYS);

        Query query = QueryBuilder.newQuery("DELETE FROM temperatures WHERE time > $start AND time < $end")
            .bind("start", start)
            .bind("end", end)
            .create();

        influxDb.query(query, MeasurementEntity.class);
    }
}
