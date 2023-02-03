package specialisation.demo.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.cassandra.config.CassandraConfig;
import specialisation.demo.cassandra.config.CassandraProperties;

@Service
@ConditionalOnBean(CassandraConfig.class)
public class FlightDbInit {

    private final CqlSession session;
    private final CqlIdentifier keyspace;

    public FlightDbInit(CqlSession session, CassandraProperties properties) {
        this.keyspace = CqlIdentifier.fromInternal(properties.keyspace());
        this.session = session;
    }

    @PostConstruct
    void setup() {
        createKeySpace();
        createFlightTable();
    }

    private void createKeySpace() {
        var statement = SchemaBuilder.createKeyspace(keyspace)
            .ifNotExists()
            .withSimpleStrategy(1)
            .build();

        session.execute(statement);
    }

    // TODO Is it possible for this table to be generated if not existing?
    private void createFlightTable() {
        // TODO Is it possible to pass the entity a custom table name instead of inferred from class name?
        var table = CqlIdentifier.fromInternal("flight_entity");

        var createTable = SchemaBuilder.createTable(keyspace, table)
            .ifNotExists()
            .withPartitionKey("flight_number", DataTypes.TEXT)
            .withColumn("creation_timestamp", DataTypes.TIMESTAMP)
            .build()
            .setKeyspace(keyspace);

        session.execute(createTable);
    }
}
