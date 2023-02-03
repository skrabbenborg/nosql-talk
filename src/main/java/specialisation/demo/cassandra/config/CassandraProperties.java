package specialisation.demo.cassandra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cassandra")
public record CassandraProperties(
    String dataCenter,
    String node,
    Integer port,
    String keyspace
) {
}
