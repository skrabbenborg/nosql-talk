package specialisation.demo.mongodb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongodb")
record MongoDbProperties(
    String url,
    String username,
    String password,
    String database
) {
}
