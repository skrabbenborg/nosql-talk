package specialisation.demo.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.mongodb.config.MongoDbConfig;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Collections.emptyList;

@Slf4j
@Service
@ConditionalOnBean(MongoDbConfig.class)
public class ReservationRepository {

    private static final String COLLECTION = "reservation";

    private final MongoCollection<ReservationEntity> collection;

    public ReservationRepository(MongoDatabase mongodb) {
        this.collection = mongodb.getCollection(COLLECTION, ReservationEntity.class);
    }

    public ReservationEntity store(ReservationRequest request) {
        ReservationEntity entity = ReservationEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(request.name())
            .date(request.date())
            .price(request.price())
            .guests(emptyList())
            .build();

        collection.insertOne(entity);

        log.info("Stored {}", entity);

        return entity;
    }

    public List<ReservationEntity> fetch(UUID id) {
        Bson query = Filters.and(
            Filters.eq("_id", id.toString())
        );

        var result = new ArrayList<ReservationEntity>();
        collection.find(query).into(result);
        return result;
    }

    public Boolean addGuest(UUID id, String name, Integer age) {
        GuestEntity guest = GuestEntity.builder()
            .name(name)
            .age(age)
            .build();

        Bson filter = Filters.eq("_id", id.toString());
        Bson update = Updates.push("guests", guest);

        var result = collection.updateOne(filter, update);
        return result.getModifiedCount() == 1L;
    }

    public List<ReservationEntity> fetchByGuestName(String name) {
        Bson idFilter = Filters.eq("guests.name", name);
        Bson guestFilter = Filters.elemMatch("guests", Filters.eq("name", name));
        Bson projection = Projections.include("guests");

        var result = new ArrayList<ReservationEntity>();
        collection.find(Filters.and(idFilter, guestFilter)).projection(projection).into(result);
        return result;
    }

    public void delete(UUID id) {
        Bson query = Filters.eq("_id", id.toString());

        collection.deleteOne(query);
    }
}
