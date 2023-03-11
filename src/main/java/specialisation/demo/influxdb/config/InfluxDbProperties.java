package specialisation.demo.influxdb.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
public record InfluxDbProperties(
    @NotNull String url,
    @NotNull String token,
    @NotNull String bucket,
    @NotNull String organisation
) {
}
