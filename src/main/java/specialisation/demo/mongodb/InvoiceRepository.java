package specialisation.demo.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.mongodb.config.MongoDbConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Slf4j
@Service
@ConditionalOnBean(MongoDbConfig.class)
public class InvoiceRepository {

    private final MongoCollection<InvoiceEntity> collection;

    public InvoiceRepository(MongoDatabase mongodb) {
        this.collection = mongodb.getCollection("invoice", InvoiceEntity.class);
    }

    public InvoiceEntity store(InvoiceRequest request) {
        InvoiceEntity entity = InvoiceEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(request.name())
            .date(request.date())
            .price(request.price())
            .build();

        collection.insertOne(entity);

        log.info("Stored {}", entity);

        return entity;
    }

    public List<InvoiceEntity> fetch(String name, LocalDate date) {
        Bson query = and(
            eq("name", name),
            eq("date", date)
        );

        var result = new ArrayList<InvoiceEntity>();

        collection.find(query).into(result);

        return result;
    }

    public void delete(UUID id) {
        Bson query = eq("id", id.toString());

        collection.deleteOne(query);
    }
}
