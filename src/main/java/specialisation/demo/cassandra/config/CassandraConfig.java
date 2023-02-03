package specialisation.demo.cassandra.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@ConditionalOnProperty(value = "cassandra.enabled", havingValue = "true")
public class CassandraConfig {

    private final CassandraProperties properties;

    public CassandraConfig(CassandraProperties properties) {
        this.properties = properties;
    }

    @Bean
    CqlSession cassandraSession() {
        return CqlSession.builder()
            .addContactPoint(new InetSocketAddress(properties.node(), properties.port()))
            .withLocalDatacenter(properties.dataCenter())
            .build();
    }

    @Bean
    Cassandra cassandraMappers(CqlSession session) {
        return new CassandraBuilder(session)
            .build();
    }
}
