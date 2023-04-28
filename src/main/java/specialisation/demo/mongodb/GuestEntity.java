package specialisation.demo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestEntity {
    @BsonProperty("name")
    String name;
    @BsonProperty("age")
    Integer age;
}