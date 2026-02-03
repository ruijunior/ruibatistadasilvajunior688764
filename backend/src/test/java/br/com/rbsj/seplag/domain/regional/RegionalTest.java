package br.com.rbsj.seplag.domain.regional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do agregado Regional")
class RegionalTest {

    @Test
    @DisplayName("Deve criar regional com dados válidos")
    void deveCriarRegionalComDadosValidos() {
        var regional = Regional.newRegional(1, "Regional Centro-Norte");

        assertNotNull(regional);

        assertNull(regional.getId());
        assertEquals("Regional Centro-Norte", regional.getNome());
        assertTrue(regional.isAtivo());
        assertNotNull(regional.getCriadoEm());
        assertNotNull(regional.getAtualizadoEm());
    }

    @Test
    @DisplayName("Deve criar regional ativo por padrão")
    void deveCriarRegionalAtivoPorPadrao() {
        var regional = Regional.newRegional(2, "Regional Sul");

        assertTrue(regional.isAtivo());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var regional = Regional.newRegional(3, "");
            regional.validate();
        });

        assertEquals("Nome da regional é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for null")
    void deveLancarExcecaoQuandoNomeNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            var regional = Regional.newRegional(4, null);
            regional.validate();
        });

        assertEquals("Nome da regional é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar regional com sucesso")
    void deveAtualizarRegionalComSucesso() {
        var regional = Regional.newRegional(5, "Regional Norte");
        var timestampOriginal = regional.getAtualizadoEm();

        regional.update("Regional Centro-Norte");

        assertEquals("Regional Centro-Norte", regional.getNome());
        assertTrue(regional.getAtualizadoEm().isAfter(timestampOriginal) || 
                   regional.getAtualizadoEm().equals(timestampOriginal));
    }

    @Test
    @DisplayName("Deve desativar regional")
    void deveDesativarRegional() {
        var regional = Regional.newRegional(6, "Regional Oeste");

        regional.desativar();

        assertFalse(regional.isAtivo());
    }

    @Test
    @DisplayName("Deve ativar regional")
    void deveAtivarRegional() {
        var id = RegionalID.from(1L);
        var now = java.time.Instant.now();
        var regional = Regional.with(id, 7, "Regional Leste", false, now, now);

        regional.ativar();

        assertTrue(regional.isAtivo());
    }

    @Test
    @DisplayName("Deve atualizar timestamp ao desativar")
    void deveAtualizarTimestampAoDesativar() {
        var regional = Regional.newRegional(8, "Regional Sul");
        var timestampOriginal = regional.getAtualizadoEm();

        regional.desativar();

        assertTrue(regional.getAtualizadoEm().isAfter(timestampOriginal) || 
                   regional.getAtualizadoEm().equals(timestampOriginal));
    }

    @Test
    @DisplayName("Deve criar regional usando método with")
    void deveCriarRegionalUsandoWith() {
        var id = RegionalID.from(2L);
        var now = java.time.Instant.now();

        var regional = Regional.with(id, 9, "Regional Sudeste", true, now, now);

        assertEquals(id, regional.getId());
        assertEquals("Regional Sudeste", regional.getNome());
        assertTrue(regional.isAtivo());
        assertEquals(now, regional.getCriadoEm());
    }

    @Test
    @DisplayName("Duas regionais com mesmo ID devem ser iguais")
    void duasRegionaisComMesmoIdDevemSerIguais() {
        var id = RegionalID.from(3L);
        var now = java.time.Instant.now();
        var regional1 = Regional.with(id, 10, "Regional 1", true, now, now);
        var regional2 = Regional.with(id, 11, "Regional 2", false, now, now);

        assertEquals(regional1, regional2);
        assertEquals(regional1.hashCode(), regional2.hashCode());
    }

    @Test
    @DisplayName("Duas regionais com IDs diferentes devem ser diferentes")
    void duasRegionaisComIdsDiferentesDevemSerDiferentes() {
        var regional1 = Regional.newRegional(12, "Regional Centro");
        var regional2 = Regional.newRegional(13, "Regional Centro");

        assertNotEquals(regional1, regional2);
    }
}
