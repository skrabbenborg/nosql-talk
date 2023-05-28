package specialisation.demo.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxQLQueryApi;
import com.influxdb.client.domain.DeletePredicateRequest;
import com.influxdb.client.domain.InfluxQLQuery;
import com.influxdb.client.domain.Query;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.dsl.Flux;

import java.time.OffsetDateTime;
import java.util.List;

import static com.influxdb.query.dsl.functions.restriction.Restrictions.measurement;

class InfluxDbRepository<T> {

    private final String org;
    private final String bucket;
    private final String measurement;
    private final Class<T> type;
    private final InfluxDBClient influxDb;

    public InfluxDbRepository(String org, String bucket, String measurement, Class<T> type, InfluxDBClient influxDb) {
        this.org = org;
        this.bucket = bucket;
        this.measurement = measurement;
        this.type = type;
        this.influxDb = influxDb;
    }

    public T store(T entity) {
        influxDb.getWriteApiBlocking().writeMeasurement(WritePrecision.MS, entity);
        return entity;
    }

    public List<T> fetch(OffsetDateTime start, OffsetDateTime end) {
        String query = Flux.from(bucket)
            .range(start.toInstant(), end.toInstant())
            .filter(measurement().equal(measurement))
            .pivot(List.of("_time"), List.of("_field"), "_value")
            .toString();

        return influxDb.getQueryApi().query(query, type);
    }

    public void delete(OffsetDateTime start, OffsetDateTime end) {
        var predicate = new DeletePredicateRequest()
            .start(start)
            .stop(end)
            .predicate("_measurement=" + measurement);

        influxDb.getDeleteApi().delete(predicate, bucket, org);
    }
}
