package br.com.rbsj.seplag.domain.album;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Value Object AlbumID")
class AlbumIDTest {

    @Test
    @DisplayName("Deve criar ID único")
    void deveCriarIdUnico() {
        var id1 = AlbumID.unique();
        var id2 = AlbumID.unique();

        assertNotNull(id1);
        assertNotNull(id1.getValue());
        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("Deve criar ID a partir de string")
    void deveCriarIdAPartirDeString() {
        var valor = "xyz-456";
        
        var id = AlbumID.from(valor);

        assertEquals(valor, id.getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção quando valor for null")
    void deveLancarExcecaoQuandoValorNull() {
        assertThrows(NullPointerException.class, () -> AlbumID.from(null));
    }

    @Test
    @DisplayName("Dois IDs com mesmo valor devem ser iguais")
    void doisIdsComMesmoValorDevemSerIguais() {
        var valor = "xyz-456";
        var id1 = AlbumID.from(valor);
        var id2 = AlbumID.from(valor);
        
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("ID deve ser lowercase")
    void idDeveSerLowercase() {
        var id = AlbumID.unique();

        assertEquals(id.getValue().toLowerCase(), id.getValue());
    }
}
