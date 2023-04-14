package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.TasksApi;
import com.influxdb.client.domain.Task;
import com.influxdb.client.domain.TaskCreateRequest;
import com.influxdb.client.domain.TaskStatusType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import specialisation.demo.influxdb.config.InfluxDbConfig;
import specialisation.demo.influxdb.config.InfluxDbProperties;

@Slf4j
@Configuration
@ConditionalOnBean(InfluxDbConfig.class)
public class TempAnalysisTask {

    private static final String TASK_NAME = "Analysis";

    private final TasksApi tasksApi;
    private final InfluxDbProperties properties;
    private final String taskFlux;

    public TempAnalysisTask(
        InfluxDBClient tasksApi,
        InfluxDbProperties properties,
        @Value("classpath:influxdb/AnalysisTask.flux") Resource taskFlux
    ) {
        this.tasksApi = tasksApi.getTasksApi();
        this.properties = properties;
        try (var fluxInputStream = taskFlux.getInputStream()) {
            this.taskFlux = new String(fluxInputStream.readAllBytes());
        } catch (Exception e) {
            throw new IllegalStateException("Could not read file from " + taskFlux);
        }
    }

    @PostConstruct
    void createTask() {
        if (tasksApi.findTasks().isEmpty()) {
            tasksApi.createTask(
                new Task()
                    .description(TASK_NAME)
                    .status(TaskStatusType.ACTIVE)
                    .flux(taskFlux)
                    .org(properties.organisation())
            );
        }
    }
}
