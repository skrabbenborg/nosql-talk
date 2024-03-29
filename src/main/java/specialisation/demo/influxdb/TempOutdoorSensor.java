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
public class TempOutdoorSensor {

    private final InfluxDbRepository<TempOutdoorEntity> repository;
    private final Random random;
    private final Clock clock;

    public TempOutdoorSensor(InfluxDbRepository<TempOutdoorEntity> repository, Clock clock) {
        this.repository = repository;
        this.random = new Random();
        this.clock = clock;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void generate() {
        var entity = TempOutdoorEntity.builder()
                .temp(random.nextInt(10))
                .address("My home address")
                .time(clock.instant())
                .build();

        repository.store(entity);

        log.info("Outdoor sensor generated " + entity);
    }
}

