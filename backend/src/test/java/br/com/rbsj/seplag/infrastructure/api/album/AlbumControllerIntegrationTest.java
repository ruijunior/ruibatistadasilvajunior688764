package br.com.rbsj.seplag.infrastructure.api.album;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@DisplayName("Testes de integração do AlbumController (API REST)")
class AlbumControllerIntegrationTest {

    public static final String PATH_ALBUM = "/api/v1/albuns";

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("catalogo_db")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Deve retornar 200 no health liveness")
    void deveRetornar200NoHealthLiveness() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health/liveness", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
    }

    @Test
    @DisplayName("Deve retornar 401 ao listar álbuns sem token")
    void deveRetornar401AoListarAlbunsSemToken() {
        ResponseEntity<String> response = restTemplate.getForEntity(PATH_ALBUM, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Deve fazer login e listar álbuns com token")
    void deveFazerLoginEListarAlbunsComToken() {
        // Login
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> loginRequest = new HttpEntity<>(
                Map.of("username", "admin", "password", "admin123"),
                loginHeaders
        );

        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
                "/api/v1/auth/login",
                loginRequest,
                Map.class
        );

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).containsKey("accessToken");
        String token = (String) loginResponse.getBody().get("accessToken");

        // Listar álbuns com token
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth(token);
        HttpEntity<Void> authRequest = new HttpEntity<>(authHeaders);

        ResponseEntity<Map> albunsResponse = restTemplate.exchange(
                PATH_ALBUM,
                HttpMethod.GET,
                authRequest,
                Map.class
        );

        assertThat(albunsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(albunsResponse.getBody()).containsKey("items");
    }
}
