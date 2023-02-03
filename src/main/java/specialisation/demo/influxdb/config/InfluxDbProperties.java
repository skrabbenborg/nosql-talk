package specialisation.demo.influxdb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
public record InfluxDbProperties(
    String url,
    String username,
    String password,
    String database
) {
}
