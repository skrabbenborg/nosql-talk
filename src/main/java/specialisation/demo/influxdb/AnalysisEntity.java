package specialisation.demo.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static specialisation.demo.influxdb.InfluxDbTypes.MEASURED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = MEASURED)
public class AnalysisEntity {
    @Column(timestamp = true)
    Instant time;
    @Column(tag = true)
    String chalet;
    @Column
    Float analysis;
}
