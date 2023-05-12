package specialisation.demo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyOverviewEntity {
    @BsonId
    String id;
    @BsonProperty("reservations")
    Long reservations;
    @BsonProperty("income")
    Integer income;
}
