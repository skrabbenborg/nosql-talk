package specialisation.demo.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static specialisation.demo.influxdb.InfluxDbTypes.INDOOR;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = INDOOR)
public class TempAnalysisEntity {
    @Column(timestamp = true)
    Instant time;
    @Column(tag = true)
    String address;
    @Column
    Float analysis;
}
