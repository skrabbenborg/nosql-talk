package specialisation.demo.influxdb.config;

import com.influxdb.LogLevel;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InfluxDbProperties.class)
@ConditionalOnProperty(value = "influxdb.enabled", havingValue = "true")
public class InfluxDbConfig {

    private final InfluxDbProperties properties;

    public InfluxDbConfig(InfluxDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    InfluxDBClient influxDb() {
        var options = new InfluxDBClientOptions.Builder()
            .url(properties.url())
            .authenticateToken(properties.token().toCharArray())
            .bucket(properties.bucket())
            .org(properties.organisation())
            .build();

        return InfluxDBClientFactory.create(options).setLogLevel(LogLevel.BODY);
    }
}
