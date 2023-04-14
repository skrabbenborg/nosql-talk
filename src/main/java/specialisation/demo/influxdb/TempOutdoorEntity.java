package specialisation.demo.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static specialisation.demo.influxdb.InfluxDbTypes.OUTDOOR;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Measurement(name = OUTDOOR)
public class TempOutdoorEntity {
    @Column(timestamp = true)
    Instant time;
    @Column(tag = true)
    String address;
    @Column
    Integer temp;
}

