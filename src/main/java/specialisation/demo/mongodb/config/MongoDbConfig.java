package specialisation.demo.mongodb.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import specialisation.demo.cassandra.config.CassandraProperties;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

@Configuration
@EnableConfigurationProperties(MongoDbProperties.class)
@ConditionalOnProperty(value = "mongodb.enabled", havingValue = "true")
public class MongoDbConfig {

    private final MongoDbProperties properties;

    public MongoDbConfig(MongoDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    MongoDatabase mongoClient() {
        String url = "mongodb://" + properties.username() + ":" + properties.password() + "@" + properties.url();

        ConnectionString connection = new ConnectionString(url);

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connection)
            .codecRegistry(codecRegistry)
            .build();

        try {
            // TODO try-with-resources closes on return statement. Does spring manage our connection?
            return MongoClients.create(settings).getDatabase(properties.database());
        } catch (Exception e) {
            throw new IllegalStateException("Could not start application due to MongoDb startup failure", e);
        }
    }
}
