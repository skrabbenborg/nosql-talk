package specialisation.demo.influxdb.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
public record InfluxDbProperties(
    @NotNull String url,
    @NotNull String username,
    @NotNull String password,
    @NotNull String database
) {
}
