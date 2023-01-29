package specialisation.demo.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import specialisation.demo.redis.config.RedisConfig;

@Service
@ConditionalOnBean(RedisConfig.class)
public class KeyRepository {

    private final Jedis jedis;

    public KeyRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    public Boolean get(Integer chaletNumber) {
        return Boolean.parseBoolean(jedis.get(chaletNumber.toString()));
    }

    public void set(Integer chaletNumber, Boolean available) {
        jedis.set(chaletNumber.toString(), available.toString());
    }
}
