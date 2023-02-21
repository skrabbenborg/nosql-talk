package specialisation.demo.neo4j.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "neo4j")
record Neo4jProperties(
    @NotNull String url,
    @NotNull String username,
    @NotNull String password
) {
}
