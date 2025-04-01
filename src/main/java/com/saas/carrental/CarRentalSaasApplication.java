package com.saas.carrental;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
    info = @Info(
        title = "Car Rental Management SaaS API",
        version = "1.0",
        description = "REST API for Car Rental Management System",
        contact = @Contact(
            name = "Support Team",
            email = "support@carrentalsaas.com"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Development Server"
        )
    },
    security = {
        @SecurityRequirement(
            name = "Bearer Authentication"
        )
    }
)
public class CarRentalSaasApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarRentalSaasApplication.class, args);
    }
}