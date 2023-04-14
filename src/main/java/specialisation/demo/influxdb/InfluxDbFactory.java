package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import specialisation.demo.influxdb.config.InfluxDbConfig;
import specialisation.demo.influxdb.config.InfluxDbProperties;

import static specialisation.demo.influxdb.InfluxDbTypes.*;

@Configuration
@ConditionalOnBean(InfluxDbConfig.class)
public class InfluxDbFactory {

    private final InfluxDbProperties properties;

    public InfluxDbFactory(InfluxDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    InfluxDbRepository<TempIndoorEntity> tempIndoorRepository(InfluxDBClient influxDb) {
        return new InfluxDbRepository<>(properties.organisation(), properties.bucket(), INDOOR, TempIndoorEntity.class, influxDb);
    }

    @Bean
    InfluxDbRepository<TempOutdoorEntity> tempOutdoorRepository(InfluxDBClient influxDb) {
        return new InfluxDbRepository<>(properties.organisation(), properties.bucket(), OUTDOOR, TempOutdoorEntity.class, influxDb);
    }

    @Bean
    InfluxDbRepository<TempAnalysisEntity> tempAnalysisRepository(InfluxDBClient influxDb) {
        return new InfluxDbRepository<>(properties.organisation(), properties.bucket(), ANALYSIS, TempAnalysisEntity.class, influxDb);
    }
}
