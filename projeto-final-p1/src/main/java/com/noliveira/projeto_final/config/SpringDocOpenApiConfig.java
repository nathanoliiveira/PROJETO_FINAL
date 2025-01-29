package com.noliveira.projeto_final.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(info = 
@io.swagger.v3.oas.annotations.info.Info(title = "Banco JAVER API",
	version = "v1",
	description = "Documentation of Banco JAVER API"))
public class SpringDocOpenApiConfig {
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(
						new Info()
								.title("REST API - Banco JAVER")
								.description("API para gestão de clientes do banco JAVER")
								.version("v1")
								.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
								.contact(new Contact().name("Nathan de Oliveira").email("nathan.oliveira05@outlook.com"))
				);
	}
}
