package kz.ctrlbee;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CtrlBeeApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(CtrlBeeApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {
		final String securitySchemeName = "bearerAuth";


		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList(securitySchemeName))
				.components(new Components()
						.addSecuritySchemes(securitySchemeName, new io.swagger.v3.oas.models.security.SecurityScheme()
								.name(securitySchemeName)
								.type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
	}
}
