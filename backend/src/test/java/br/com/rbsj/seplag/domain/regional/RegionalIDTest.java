package br.com.rbsj.seplag.domain.regional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Value Object RegionalID")
class RegionalIDTest {

    @Test
    @DisplayName("Deve criar ID único")
    void deveCriarIdUnico() {
        var id1 = RegionalID.unique();
        var id2 = RegionalID.unique();

        assertNotNull(id1);
        assertNotNull(id1.getValue());
        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("Deve criar ID a partir de string")
    void deveCriarIdAPartirDeString() {
        var valor = "regional-123";
        
        var id = RegionalID.from(valor);

        assertEquals(valor, id.getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção quando valor for null")
    void deveLancarExcecaoQuandoValorNull() {
        assertThrows(NullPointerException.class, () -> RegionalID.from(null));
    }

    @Test
    @DisplayName("Dois IDs com mesmo valor devem ser iguais")
    void doisIdsComMesmoValorDevemSerIguais() {
        var valor = "regional-123";
        var id1 = RegionalID.from(valor);
        var id2 = RegionalID.from(valor);
        
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("ID deve ser lowercase")
    void idDeveSerLowercase() {
        var id = RegionalID.unique();

        assertEquals(id.getValue().toLowerCase(), id.getValue());
    }
}
