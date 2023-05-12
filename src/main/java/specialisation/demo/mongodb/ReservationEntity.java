package specialisation.demo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    @BsonId
    String id;
    @BsonProperty("price")
    Long price;
    @BsonProperty("date")
    LocalDate date;
    @BsonProperty("name")
    String name;
}
