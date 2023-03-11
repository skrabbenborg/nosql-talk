package specialisation.demo.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = "measured")
public class MeasurementEntity {
    @Column(timestamp = true)
    Instant time;
    @Column(tag = true)
    String chalet;
    @Column
    Integer temp;
}
