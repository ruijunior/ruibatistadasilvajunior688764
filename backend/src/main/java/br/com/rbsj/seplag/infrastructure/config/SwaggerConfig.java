package br.com.rbsj.seplag.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI.
 * Documenta automaticamente a API REST.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Catálogo de Artistas e Álbuns")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de artistas e álbuns - Processo Seletivo SEPLAG")
                        .contact(new Contact()
                                .name("Rui Batista da Silva Junior")
                                .email("ruibatistasilvajunior@gmail.com")));
    }
}
