package specialisation.demo.cassandra.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cassandra")
public record CassandraProperties(
    @NotNull String dataCenter,
    @NotNull String node,
    @NotNull Integer port,
    @NotNull String keyspace
) {
}
