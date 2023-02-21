package specialisation.demo.redis.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
record RedisProperties(
    @NotNull String url,
    @NotNull String username,
    @NotNull String password
) {
}
