package specialisation.demo.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.cassandra.config.Cassandra;
import specialisation.demo.cassandra.config.CassandraConfig;
import specialisation.demo.cassandra.config.CassandraProperties;

import java.time.Clock;
import java.util.Optional;

@Slf4j
@Service
@ConditionalOnBean(CassandraConfig.class)
public class FlightRepository {

    private final Cassandra dao;
    private final CqlIdentifier keyspace;
    private final Clock clock;

    public FlightRepository(Cassandra dao, CassandraProperties properties, Clock clock) {
        this.dao = dao;
        this.keyspace = CqlIdentifier.fromInternal(properties.keyspace());
        this.clock = clock;
    }

    public FlightEntity store(FlightRequest request) {
        var entity = FlightEntity.builder()
            .flightNumber(request.flightNumber())
            .creationTimestamp(clock.instant())
            .build();

        dao.flightEntityDao(keyspace).save(entity);
        log.info("Stored {}", entity);

        return entity;
    }

    public Optional<FlightEntity> fetch(String flightNumber) {
        return dao.flightEntityDao(keyspace).findById(flightNumber);
    }

    public void delete(String flightNumber) {
        fetch(flightNumber).ifPresent((entity) -> dao.flightEntityDao(keyspace).delete(entity));
    }
}
