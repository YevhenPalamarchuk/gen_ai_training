package com.epam.training.gen.ai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for setting up Swagger/OpenAPI documentation
 * for the Gen AI Training application.
 *
 * This class defines a bean to create a custom `OpenAPI` instance that encapsulates
 * metadata used to generate comprehensive API documentation, including information
 * such as the API title, version, and description.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures a custom OpenAPI bean for the Gen AI Training application.
     * The OpenAPI instance defines meta-information for the API, such as title, version, and description.
     *
     * @return an OpenAPI instance containing the API's metadata for documentation purposes
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gen AI Training API")
                        .version("1.0")
                        .description("API documentation for the Gen AI Training application"));
    }
}
