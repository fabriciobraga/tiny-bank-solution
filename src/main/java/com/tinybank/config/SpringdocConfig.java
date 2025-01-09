package com.tinybank.config;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenApiCustomizer customiseGlobalResponses() {
        return openApi -> {
            openApi.getPaths().forEach((path, pathItem) -> {
                pathItem.readOperations().forEach(operation -> addGlobalResponses(operation));
            });
        };
    }

    private void addGlobalResponses(Operation operation) {
        ApiResponses responses = operation.getResponses();

        if (!responses.containsKey("500")) {
            responses.addApiResponse("500", new ApiResponse().description("Internal Server Error"));
        }
    }
}
