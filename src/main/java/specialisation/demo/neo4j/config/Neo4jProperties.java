package specialisation.demo.neo4j.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "neo4j")
record Neo4jProperties(
    String url,
    String username,
    String password
) {
}
