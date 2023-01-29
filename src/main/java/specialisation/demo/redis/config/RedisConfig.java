package specialisation.demo.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.net.URI;

@Configuration
@ConditionalOnProperty(value = "redis.enabled", havingValue = "true")
public class RedisConfig {

    private final RedisProperties properties;

    public RedisConfig(RedisProperties properties) {
        this.properties = properties;
    }

    @Bean
    Jedis redisClient() {
        Jedis jedis = new Jedis(URI.create(properties.url()));
        jedis.auth(properties.username(), properties.password());
        jedis.connect();
        return jedis;
    }
}
