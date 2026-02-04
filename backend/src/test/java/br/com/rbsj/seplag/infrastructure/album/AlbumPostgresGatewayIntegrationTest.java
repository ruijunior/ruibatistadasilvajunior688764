package br.com.rbsj.seplag.infrastructure.album;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumID;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;
import br.com.rbsj.seplag.infrastructure.album.persistence.AlbumRepository;
import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaJpaEntity;
import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaRepository;
import br.com.rbsj.seplag.infrastructure.artista.persistence.TipoArtistaJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@DisplayName("Testes de integração do AlbumPostgresGateway")
class AlbumPostgresGatewayIntegrationTest {

    public static final String ALBUM_PARA_FIND = "Álbum para Find";
    public static final String ID_INEXISTENTE = "id-inexistente";
    public static final String A_PRIMEIRO = "A primeiro";
    public static final String B_SEGUNDO = "B segundo";
    public static final String TITULO = "titulo";
    public static final String ALBUM_SOLO = "Álbum Solo";
    public static final String ALBUM_COM_ARTISTAS = "Álbum com Artistas";
    public static final String ASC = "asc";
    public static final String TITULO_ATUALIZADO = "Título Atualizado";

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
    private AlbumPostgresGateway gateway;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    private ArtistaJpaEntity artistaCantor;
    private ArtistaJpaEntity artistaBanda;

    @BeforeEach
    void setUp() {
        albumRepository.deleteAll();
        artistaRepository.deleteAll();
        artistaCantor = artistaRepository.save(new ArtistaJpaEntity(
                "artista-cantor-1",
                "Cantor Teste",
                TipoArtistaJpa.CANTOR
        ));
        artistaBanda = artistaRepository.save(new ArtistaJpaEntity(
                "artista-banda-1",
                "Banda Teste",
                TipoArtistaJpa.BANDA
        ));
    }

    @Test
    @DisplayName("Deve criar álbum sem artistas")
    void deveCriarAlbumSemArtistas() {
        var album = Album.newAlbum(ALBUM_SOLO, 2020);
        album.validate();

        var created = gateway.create(album);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getTitulo()).isEqualTo(ALBUM_SOLO);
        assertThat(created.getAnoLancamento()).isEqualTo(2020);
        assertThat(created.getArtistas()).isEmpty();
        assertThat(created.getCapas()).isEmpty();
    }

    @Test
    @DisplayName("Deve criar álbum com artistas")
    void deveCriarAlbumComArtistas() {
        var album = Album.newAlbum(ALBUM_COM_ARTISTAS, 2021);
        album.addArtista(ArtistaID.from(artistaCantor.getId()));
        album.addArtista(ArtistaID.from(artistaBanda.getId()));
        album.validate();

        var created = gateway.create(album);

        assertThat(created).isNotNull();
        assertThat(created.getTitulo()).isEqualTo(ALBUM_COM_ARTISTAS);
        assertThat(created.getArtistas()).hasSize(2);
    }

    @Test
    @DisplayName("Deve buscar álbum por ID")
    void deveBuscarAlbumPorId() {
        var album = Album.newAlbum(ALBUM_PARA_FIND, 2019);
        var created = gateway.create(album);

        var found = gateway.findById(created.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId().getValue()).isEqualTo(created.getId().getValue());
        assertThat(found.get().getTitulo()).isEqualTo(ALBUM_PARA_FIND);
    }

    @Test
    @DisplayName("Deve retornar vazio quando álbum não existe")
    void deveRetornarVazioQuandoAlbumNaoExiste() {
        var found = gateway.findById(AlbumID.from(ID_INEXISTENTE));

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Deve listar álbuns com paginação")
    void deveListarAlbunsComPaginacao() {
        gateway.create(Album.newAlbum(A_PRIMEIRO, 2020));
        gateway.create(Album.newAlbum(B_SEGUNDO, 2021));
        gateway.create(Album.newAlbum("C terceiro", 2022));

        var query = new SearchQuery(0, 2, null, TITULO, ASC, null);
        var result = gateway.findAll(query);

        assertThat(result.items()).hasSize(2);
        assertThat(result.currentPage()).isZero();
        assertThat(result.perPage()).isEqualTo(2);
        assertThat(result.total()).isEqualTo(3);
        assertThat(result.items().get(0).getTitulo()).isEqualTo(A_PRIMEIRO);
        assertThat(result.items().get(1).getTitulo()).isEqualTo(B_SEGUNDO);
    }

    @Test
    @DisplayName("Deve filtrar álbuns por termo (título)")
    void deveFiltrarAlbunsPorTermo() {
        gateway.create(Album.newAlbum("Rock Nacional", 2020));
        gateway.create(Album.newAlbum("Sertanejo Raiz", 2021));
        gateway.create(Album.newAlbum("Rock Internacional", 2022));

        var query = new SearchQuery(0, 10, "Rock", TITULO, ASC, null);
        var result = gateway.findAll(query);

        assertThat(result.items()).hasSize(2);
        assertThat(result.items()).extracting(Album::getTitulo)
                .containsExactlyInAnyOrder("Rock Nacional", "Rock Internacional");
    }

    @Test
    @DisplayName("Deve filtrar álbuns por tipo de artista (CANTOR)")
    void deveFiltrarAlbunsPorTipoCantor() {
        var albumCantor = Album.newAlbum("Só Cantor", 2020);
        albumCantor.addArtista(ArtistaID.from(artistaCantor.getId()));
        gateway.create(albumCantor);

        var albumBanda = Album.newAlbum("Só Banda", 2021);
        albumBanda.addArtista(ArtistaID.from(artistaBanda.getId()));
        gateway.create(albumBanda);

        var query = new SearchQuery(0, 10, null, TITULO, "asc", "CANTOR");
        var result = gateway.findAll(query);

        assertThat(result.items()).hasSize(1);
        assertThat(result.items().get(0).getTitulo()).isEqualTo("Só Cantor");
    }

    @Test
    @DisplayName("Deve atualizar álbum")
    void deveAtualizarAlbum() {
        var album = Album.newAlbum("Título Original", 2018);
        var created = gateway.create(album);

        created.update("Título Atualizado", 2019);
        var updated = gateway.update(created);

        assertThat(updated.getTitulo()).isEqualTo(TITULO_ATUALIZADO);
        assertThat(updated.getAnoLancamento()).isEqualTo(2019);

        var found = gateway.findById(created.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitulo()).isEqualTo(TITULO_ATUALIZADO);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar álbum inexistente")
    void deveLancarExcecaoAoAtualizarAlbumInexistente() {
        var album = Album.with(
                AlbumID.from(ID_INEXISTENTE),
                "Título",
                2020,
                Set.of(),
                Instant.now(),
                Instant.now()
        );

        assertThatThrownBy(() -> gateway.update(album))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Album not found");
    }
}
