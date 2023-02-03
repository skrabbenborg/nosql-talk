package specialisation.demo.cassandra.config;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import specialisation.demo.cassandra.FlightDao;

@Mapper
public interface Cassandra {

    @DaoFactory
    FlightDao flightEntityDao(@DaoKeyspace CqlIdentifier keyspace);
}
