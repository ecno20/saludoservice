package unam.diplomado.kubernetes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
			version = "v1",
			title = "Aplicación para ejercicio 15-Test", 
			description = "Definición de mensaje "
					+ "para ejemplo de servicio Rest", 
			contact = @Contact(
				name = "UNAM", 
				url = "https://www.unam.mx/", 
			email = "jdsmatemaster@gmail.com")))
public class OpenApiConfig {

}

