package specialisation.demo.mongodb.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongodb")
record MongoDbProperties(
    @NotNull String url,
    @NotNull String username,
    @NotNull String password,
    @NotNull String database
) {
}
