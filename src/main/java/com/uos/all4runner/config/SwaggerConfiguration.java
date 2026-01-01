package com.uos.all4runner.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "All 4 Runner",
		version = "0.0.1",
		description = "All 4 Runner API 명세"
	)
)
@SecurityRequirement(name = "Bearer Authentication")
public class SwaggerConfiguration {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(
				new Components()
					.addSecuritySchemes(
						"Bearer Authentication",
						new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
					)
			);
	}

	@Bean
	public GroupedOpenApi AdminApi() {
		return GroupedOpenApi.builder()
			// 해당 URL 패턴을 가지는 API만 포함
			.pathsToMatch("/api/admin/**", "/api/auth/**")
			.group("Admin API")
			.build();
	}

	@Bean
	public GroupedOpenApi UserApi() {
		return GroupedOpenApi.builder()
			.group("User API")
			// 해당 URL 패턴을 제외한 API만 포함
			.pathsToExclude("/api/admin/**")
			.build();
	}

}
