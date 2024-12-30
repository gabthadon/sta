package com.softnet.sta;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "STA REST APIs",
				description = "STA REST API ENDPOINTS DOCUMENTATIONS",
				version = "v1.0.0",
				contact = @Contact(
						name = "STA",
						email = "info@sta.com",
						url = "https://www.sta.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.sta.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "STA REST API ENDPOINTS DOCUMENTATIONS",
				url = "https://www.sta.com"
		)
)

@SpringBootApplication
public class StaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaApplication.class, args);
	}

}
