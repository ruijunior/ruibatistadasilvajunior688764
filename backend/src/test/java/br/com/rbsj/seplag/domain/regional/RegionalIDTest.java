package br.com.rbsj.seplag.domain.regional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Value Object RegionalID")
class RegionalIDTest {

    @Test
    @DisplayName("Deve criar ID a partir de Long")
    void deveCriarIdAPartirDeLong() {
        Long valor = 123L;
        var id = RegionalID.from(valor);

        assertNotNull(id);
        assertEquals(valor, id.getValue());
    }

    @Test
    @DisplayName("Deve criar ID a partir de String numérica")
    void deveCriarIdAPartirDeStringNumerica() {
        var valor = "123";
        var id = RegionalID.from(valor);

        assertEquals(123L, id.getValue());
    }

    @Test
    @DisplayName("Deve permitir null no construtor se necessário ou lidar com NumberFormatException")
    void deveLancarExcecaoSeStringNaoNumerica() {
        assertThrows(NumberFormatException.class, () -> RegionalID.from("abc"));
    }

    @Test
    @DisplayName("Dois IDs com mesmo valor devem ser iguais")
    void doisIdsComMesmoValorDevemSerIguais() {
        Long valor = 123L;
        var id1 = RegionalID.from(valor);
        var id2 = RegionalID.from(valor);
        
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
}
