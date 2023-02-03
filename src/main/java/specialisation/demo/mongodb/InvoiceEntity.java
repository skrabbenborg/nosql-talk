package specialisation.demo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity {
    @BsonProperty("id")
    String id;
    @BsonProperty("price")
    Long price;
    @BsonProperty("date")
    LocalDate date;
    @BsonProperty("name")
    String name;
}
