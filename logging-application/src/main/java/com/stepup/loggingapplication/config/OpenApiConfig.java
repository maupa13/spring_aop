package com.stepup.loggingapplication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@code OpenApiConfig} class is a Spring configuration class responsible for defining
 * bean configurations related to OpenAPI documentation in the producer application.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Provides a bean definition for customizing the OpenAPI documentation for the producer service.
     *
     * @return A customized instance of OpenAPI representing the producer service API documentation.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Logging_application")
                        .version("1.0")
                        .description("Register, Login with Spring Security using Aspects")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("maupa13")));
    }
}
