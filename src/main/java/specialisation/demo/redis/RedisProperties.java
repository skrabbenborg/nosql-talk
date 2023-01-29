package specialisation.demo.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
record RedisProperties(
    String url,
    String username,
    String password
) {
}
