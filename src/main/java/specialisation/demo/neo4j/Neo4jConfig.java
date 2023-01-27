package specialisation.demo.neo4j;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Neo4jConfig {

    private final Neo4jProperties properties;

    public Neo4jConfig(Neo4jProperties properties) {
        this.properties = properties;
    }

    @Bean
    SessionFactory neo4jClient() {

        Configuration configuration = new Configuration.Builder()
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
