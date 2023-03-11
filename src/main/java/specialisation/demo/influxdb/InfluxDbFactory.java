package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import specialisation.demo.influxdb.config.InfluxDbProperties;

import static specialisation.demo.influxdb.InfluxDbTypes.*;

@Configuration
public class InfluxDbFactory {

    private final InfluxDbProperties properties;

    public InfluxDbFactory(InfluxDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    InfluxDbRepository<MeasurementEntity> measurementRepository(InfluxDBClient influxDb) {
        return new InfluxDbRepository<>(properties.organisation(), properties.bucket(), MEASURED, MeasurementEntity.class, influxDb);
    }

    @Bean
    InfluxDbRepository<PrognosisEntity> prognosisRepository(InfluxDBClient influxDb) {
        return new InfluxDbRepository<>(properties.organisation(), properties.bucket(), PROGNOSED, PrognosisEntity.class, influxDb);
    }

    @Bean
    InfluxDbRepository<AnalysisEntity> analysisRepository(InfluxDBClient influxDb) {
        return new InfluxDbRepository<>(properties.organisation(), properties.bucket(), ANALYSIS, AnalysisEntity.class, influxDb);
    }
}
