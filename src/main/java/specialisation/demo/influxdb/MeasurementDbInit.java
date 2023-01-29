package specialisation.demo.influxdb;

import jakarta.annotation.PostConstruct;
import org.influxdb.InfluxDB;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.influxdb.config.InfluxDbConfig;
import specialisation.demo.influxdb.config.InfluxDbProperties;

@Service
@ConditionalOnBean(InfluxDbConfig.class)
public class MeasurementDbInit {

    private final InfluxDB influxDb;
    private final InfluxDbProperties properties;

    public MeasurementDbInit(InfluxDB influxDb, InfluxDbProperties properties) {
        this.influxDb = influxDb;
        this.properties = properties;
    }

    @PostConstruct
    void createDatabase() {
        // TODO Replace deprecated method with query
        influxDb.createDatabase(properties.database());
    }
}
