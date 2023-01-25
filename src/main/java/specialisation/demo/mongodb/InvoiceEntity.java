package specialisation.demo.mongodb;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.UUID;

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
