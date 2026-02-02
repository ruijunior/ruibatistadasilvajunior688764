package br.com.rbsj.seplag.domain.artista;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do agregado Artista")
class ArtistaTest {

    @Test
    @DisplayName("Deve criar artista com dados válidos")
    void deveCriarArtistaComDadosValidos() {
        var artista = Artista.newArtista("Serj Tankian", TipoArtista.CANTOR);

        assertNotNull(artista);
        assertNotNull(artista.getId());
        assertEquals("Serj Tankian", artista.getNome());
        assertEquals(TipoArtista.CANTOR, artista.getTipo());
        assertNotNull(artista.getCriadoEm());
        assertNotNull(artista.getAtualizadoEm());
    }

    @Test
    @DisplayName("Deve criar artista do tipo BANDA")
    void deveCriarArtistaTipoBanda() {
        var artista = Artista.newArtista("Guns N' Roses", TipoArtista.BANDA);

        assertEquals("Guns N' Roses", artista.getNome());
        assertEquals(TipoArtista.BANDA, artista.getTipo());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var artista = Artista.newArtista("", TipoArtista.CANTOR);
            artista.validate();
        });

        assertEquals("Nome do artista é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for null")
    void deveLancarExcecaoQuandoNomeNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var artista = Artista.newArtista(null, TipoArtista.CANTOR);
            artista.validate();
        });

        assertEquals("Nome do artista é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo for null")
    void deveLancarExcecaoQuandoTipoNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var artista = Artista.newArtista("Serj Tankian", null);
            artista.validate();
        });

        assertEquals("Tipo do artista é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar artista com sucesso")
    void deveAtualizarArtistaComSucesso() {
        var artista = Artista.newArtista("Serj", TipoArtista.CANTOR);
        var timestampOriginal = artista.getAtualizadoEm();

        artista.update("Serj Tankian", TipoArtista.BANDA);

        assertEquals("Serj Tankian", artista.getNome());
        assertEquals(TipoArtista.BANDA, artista.getTipo());
        assertTrue(artista.getAtualizadoEm().isAfter(timestampOriginal) || 
                   artista.getAtualizadoEm().equals(timestampOriginal));
    }

    @Test
    @DisplayName("Deve criar artista usando método with")
    void deveCriarArtistaUsandoWith() {
        var id = ArtistaID.unique();
        var now = java.time.Instant.now();

        var artista = Artista.with(id, "Mike Shinoda", TipoArtista.CANTOR, now, now);

        assertEquals(id, artista.getId());
        assertEquals("Mike Shinoda", artista.getNome());
        assertEquals(TipoArtista.CANTOR, artista.getTipo());
        assertEquals(now, artista.getCriadoEm());
        assertEquals(now, artista.getAtualizadoEm());
    }

    @Test
    @DisplayName("Dois artistas com mesmo ID devem ser iguais")
    void doisArtistasComMesmoIdDevemSerIguais() {
        var id = ArtistaID.unique();
        var now = java.time.Instant.now();
        var artista1 = Artista.with(id, "Artista 1", TipoArtista.CANTOR, now, now);
        var artista2 = Artista.with(id, "Artista 2", TipoArtista.BANDA, now, now);

        assertEquals(artista1, artista2);
        assertEquals(artista1.hashCode(), artista2.hashCode());
    }

    @Test
    @DisplayName("Dois artistas com IDs diferentes devem ser diferentes")
    void doisArtistasComIdsDiferentesDevemSerDiferentes() {
        var artista1 = Artista.newArtista("Artista 1", TipoArtista.CANTOR);
        var artista2 = Artista.newArtista("Artista 1", TipoArtista.CANTOR);

        assertNotEquals(artista1, artista2);
    }
}
