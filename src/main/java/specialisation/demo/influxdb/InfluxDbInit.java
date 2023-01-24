package specialisation.demo.influxdb;

import jakarta.annotation.PostConstruct;
import org.influxdb.InfluxDB;
import org.springframework.stereotype.Service;

@Service
public class InfluxDbInit {

    private final InfluxDB influxDb;
    private final InfluxDbProperties properties;

    public InfluxDbInit(InfluxDB influxDb, InfluxDbProperties properties) {
        this.influxDb = influxDb;
        this.properties = properties;
    }

    @PostConstruct
    void createDatabase() {
        // TODO Replace deprecated method with query
        influxDb.createDatabase(properties.database());
    }
}
