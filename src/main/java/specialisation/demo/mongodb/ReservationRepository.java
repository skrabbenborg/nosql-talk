package specialisation.demo.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonDocument;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.mongodb.config.MongoDbConfig;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@ConditionalOnBean(MongoDbConfig.class)
public class ReservationRepository {

    private static final String RESERVATION_COLLECTION = "reservations";
    private static final String OVERVIEW_COLLECTION = "overviews";

    private final MongoCollection<ReservationEntity> reservations;
    private final MongoCollection<MonthlyOverviewEntity> overviews;

    public ReservationRepository(MongoDatabase mongodb) {
        this.reservations = mongodb.getCollection(RESERVATION_COLLECTION, ReservationEntity.class);
        this.overviews = mongodb.getCollection(OVERVIEW_COLLECTION, MonthlyOverviewEntity.class);
    }

    public ReservationEntity fetchReservation(UUID id) {
        return reservations.find(Filters.eq("_id", id.toString()))
            .first();
    }

    public MonthlyOverviewEntity fetchOverview(Month month, Year year) {
        return overviews.find(Filters.eq("_id", String.format("%s-%s", year, month)))
            .first();
    }

    public ReservationEntity store(ReservationRequest request) {
        ReservationEntity entity = ReservationEntity.builder()
            .id(UUID.randomUUID().toString())
            .name(request.name())
            .date(request.date())
            .price(request.price())
            .build();

        reservations.insertOne(entity);
        log.info("Stored {}", entity);

        var overview = updateMonthlyOverview(entity.date.getMonth(), entity.date.getYear());
        log.info("Updated {}", overview);

        return entity;
    }

    private BsonDocument updateMonthlyOverview(Month month, Integer year) {
        LocalDate start = Year.of(year).atMonth(month).atDay(1);
        LocalDate end = Year.of(year).atMonth(month).atEndOfMonth();

        var id = String.format("%s-%s", year, month);

        var pipeline = List.of(
            Aggregates.match(Filters.gte("date", start)),
            Aggregates.match(Filters.lte("date", end)),
            Aggregates.group(id,
                Accumulators.sum("income", "$price"),
                Accumulators.sum("reservations", 1)
            ),
            Aggregates.out(OVERVIEW_COLLECTION)
        );

        return reservations.aggregate(pipeline, BsonDocument.class).first();
    }
}
