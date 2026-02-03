package br.com.rbsj.seplag.domain.album;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do agregado Album")
class AlbumTest {

    @Test
    @DisplayName("Deve criar album com dados válidos")
    void deveCriarAlbumComDadosValidos() {
        var album = Album.newAlbum("Album Test 1", 2012);

        assertNotNull(album);
        assertNotNull(album.getId());
        assertEquals("Album Test 1", album.getTitulo());
        assertEquals(2012, album.getAnoLancamento());
        assertTrue(album.getCapas().isEmpty());
        assertNotNull(album.getCriadoEm());
        assertNotNull(album.getAtualizadoEm());
    }

    @Test
    @DisplayName("Deve criar album sem ano de lançamento")
    void deveCriarAlbumSemAnoLancamento() {
        var album = Album.newAlbum("Post Test 1", null);

        assertEquals("Post Test 1", album.getTitulo());
        assertNull(album.getAnoLancamento());
    }

    @Test
    @DisplayName("Deve lançar exceção quando titulo for vazio")
    void deveLancarExcecaoQuandoTituloVazio() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var album = Album.newAlbum("", 2012);
            album.validate();
        });

        assertEquals("Título do álbum é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando titulo for null")
    void deveLancarExcecaoQuandoTituloNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var album = Album.newAlbum(null, 2012);
            album.validate();
        });

        assertEquals("Título do álbum é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar album com sucesso")
    void deveAtualizarAlbumComSucesso() {
        var album = Album.newAlbum("Post Test 1 EP", 2018);
        var timestampOriginal = album.getAtualizadoEm();

        album.update("Post Test 1", 2018);

        assertEquals("Post Test 1", album.getTitulo());
        assertEquals(2018, album.getAnoLancamento());
        assertTrue(album.getAtualizadoEm().isAfter(timestampOriginal) || 
                   album.getAtualizadoEm().equals(timestampOriginal));
    }
    
    @Test
    @DisplayName("Deve adicionar capa ao álbum")
    void deveAdicionarCapaAoAlbum() {
        var album = Album.newAlbum("Use sua imaginacao I", 1991);

        album.addCapa("https://minio.local/capas/use-sua-imaginacao-1.jpg");

        assertTrue(album.getCapas().contains("https://minio.local/capas/use-sua-imaginacao-1.jpg"));
    }

    @Test
    @DisplayName("Deve criar album usando método with")
    void deveCriarAlbumUsandoWith() {
        var id = AlbumID.unique();
        var now = java.time.Instant.now();
        var capas = java.util.Set.of("url-capa");

        var album = Album.with(id, "Bem Sertanejo", 2009, capas, now, now);

        assertEquals(id, album.getId());
        assertEquals("Bem Sertanejo", album.getTitulo());
        assertEquals(2009, album.getAnoLancamento());
        assertEquals(capas, album.getCapas());
    }

    @Test
    @DisplayName("Dois albuns com mesmo ID devem ser iguais")
    void doisAlbunsComMesmoIdDevemSerIguais() {
        var id = AlbumID.unique();
        var now = java.time.Instant.now();
        var album1 = Album.with(id, "Album 1", 2020, java.util.Collections.emptySet(), now, now);
        var album2 = Album.with(id, "Album 2", 2021, java.util.Collections.emptySet(), now, now);

        assertEquals(album1, album2);
        assertEquals(album1.hashCode(), album2.hashCode());
    }

    @Test
    @DisplayName("Dois albuns com IDs diferentes devem ser diferentes")
    void doisAlbunsComIdsDiferentesDevemSerDiferentes() {
        var album1 = Album.newAlbum("Hits Test 1", 2004);
        var album2 = Album.newAlbum("Hits Test 1", 2004);

        assertNotEquals(album1, album2);
    }
}
