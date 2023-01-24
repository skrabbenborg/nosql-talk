package specialisation.demo.influxdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
record InfluxDbProperties(
    String url,
    String username,
    String password,
    String database
) {
}
