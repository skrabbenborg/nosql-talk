package specialisation.demo.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mongodb")
record MongoDbProperties(
    String url,
    String username,
    String password,
    String database
) {
}
