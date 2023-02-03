package specialisation.demo.influxdb.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import specialisation.demo.cassandra.config.CassandraProperties;

@Configuration
@EnableConfigurationProperties(InfluxDbProperties.class)
@ConditionalOnProperty(value = "influxdb.enabled", havingValue = "true")
public class InfluxDbConfig {

    private final InfluxDbProperties properties;

    public InfluxDbConfig(InfluxDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    InfluxDB influxDb() {
        try (InfluxDB influxDB = InfluxDBFactory
            .connect(properties.url(), properties.url(), properties.password())
            .setDatabase(properties.database())
            .enableBatch()
        ) {
            return influxDB;
        } catch (Exception e) {
            throw new IllegalStateException("Could not start application due to InfluxDB startup failure", e);
        }
    }

    @Bean
    InfluxDBMapper influxDbMapper(InfluxDB influxDB) {
        return new InfluxDBMapper(influxDB);
    }
}
