package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.TasksApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import specialisation.demo.influxdb.config.InfluxDbConfig;

@Slf4j
@Configuration
@ConditionalOnBean(InfluxDbConfig.class)
public class AnalysisTask {

    private static final String TASK_NAME = "Perform analysis";

    private final TasksApi tasksApi;
    private final String taskFlux;

    // TODO Fix, doesnt work
    public AnalysisTask(InfluxDBClient tasksApi, @Value("classpath:influxdb/AnalysisTask.flux") Resource taskFlux) {
        this.tasksApi = tasksApi.getTasksApi();
        try (var fluxInputStream = taskFlux.getInputStream()) {
            this.taskFlux = new String(fluxInputStream.readAllBytes());
        } catch (Exception e) {
            throw new IllegalStateException("Could not read file from " + taskFlux);
        }
    }

    void createTask() {
        if (tasksApi.findTasks().stream().noneMatch((it) -> it.getName().equals(TASK_NAME))) {
            var task = tasksApi.createTaskCron(TASK_NAME, taskFlux, "0-59 * * * *", "org");
            log.info("Analysis task created: " + task);
        } else {
            log.info("Analysis task already exists");
        }
    }
}
