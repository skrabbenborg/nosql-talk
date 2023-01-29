package specialisation.demo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi springShopOpenAPI() {
        return GroupedOpenApi.builder()
            .group("Databases")
            .pathsToMatch("/**")
            .build();
    }
}
