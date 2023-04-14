package specialisation.demo.influxdb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import specialisation.demo.influxdb.config.InfluxDbConfig;

import java.time.Clock;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConditionalOnBean(InfluxDbConfig.class)
public class TempIndoorSensor {

    private final InfluxDbRepository<TempIndoorEntity> repository;
    private final Random random;
    private final Clock clock;

    public TempIndoorSensor(InfluxDbRepository<TempIndoorEntity> repository, Clock clock) {
        this.repository = repository;
        this.random = new Random();
        this.clock = clock;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void generate() {
        var entity = TempIndoorEntity.builder()
            .temp(random.nextInt(10))
            .address("My home address")
            .time(clock.instant())
            .build();

        repository.store(entity);

        log.info("Sensor generated " + entity);
    }
}
