package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.Task;
import com.influxdb.query.dsl.Flux;
import org.springframework.stereotype.Service;
import specialisation.demo.influxdb.config.InfluxDbProperties;

import java.time.temporal.ChronoUnit;

import static com.influxdb.query.dsl.functions.restriction.Restrictions.*;
import static specialisation.demo.influxdb.InfluxDbTypes.*;

@Service
public class AnalysisTask {

    private final InfluxDBClient influxDb;
    private final InfluxDbProperties properties;

    public AnalysisTask(InfluxDBClient influxDb, InfluxDbProperties properties) {
        this.influxDb = influxDb;
        this.properties = properties;
    }

//    @PostConstruct
    void createTask() {

        Flux measured = Flux.from(properties.bucket())
            .filter(and(measurement().equal(MEASURED), and(field().equal("chalet"))))
            .range(-1L, ChronoUnit.MINUTES)
            .mean();

        Flux prognosed = Flux.from(properties.bucket())
            .filter(and(measurement().equal(PROGNOSED), and(field().equal("chalet"))))
            .range(-1L, ChronoUnit.MINUTES)
            .mean();

        Flux flux = Flux.join()
            .withTable(MEASURED, measured)
            .withTable(PROGNOSED, prognosed)
            .withOn(ANALYSIS);

        var task = new Task()
            .org(properties.organisation())
            .name("Analysis")
            .flux(flux.toString());

        influxDb.getTasksApi().createTask(task);
    }
}
