package specialisation.demo.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import specialisation.demo.cassandra.config.CassandraProperties;

@Configuration
@EnableConfigurationProperties(Neo4jProperties.class)
@ConditionalOnProperty(value = "neo4j.enabled", havingValue = "true")
public class Neo4jConfig {

    private final Neo4jProperties properties;

    public Neo4jConfig(Neo4jProperties properties) {
        this.properties = properties;
    }

    @Bean
    SessionFactory neo4jClient() {

        var configuration = new org.neo4j.ogm.config.Configuration.Builder()
            .uri(properties.url())
            .credentials(properties.username(), properties.password())
            .build();

        try {
            return new SessionFactory(configuration, "specialisation.demo");
        } catch (Exception e) {
            throw new IllegalStateException("Could not start application due to Neo4j startup failure", e);
        }
    }
}
