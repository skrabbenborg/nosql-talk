package specialisation.demo.elastic.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elastic")
record ElasticsearchProperties(
    @NotNull String url,
    @NotNull String username,
    @NotNull String password
) {
}
