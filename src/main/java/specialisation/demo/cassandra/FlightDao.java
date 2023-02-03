package specialisation.demo.cassandra;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Select;

import java.util.Optional;

@Dao
public interface FlightDao {

    @Select
    Optional<FlightEntity> findById(String flightNumber);

    @Insert
    void save(FlightEntity entity);

    @Delete
    void delete(FlightEntity entity);
}
