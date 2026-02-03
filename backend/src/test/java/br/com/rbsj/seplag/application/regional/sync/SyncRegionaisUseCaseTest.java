package br.com.rbsj.seplag.application.regional.sync;

import br.com.rbsj.seplag.domain.regional.Regional;
import br.com.rbsj.seplag.domain.regional.RegionalGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do SyncRegionaisUseCase")
class SyncRegionaisUseCaseTest {

    @Mock
    private RegionalGateway regionalGateway;

    @Mock
    private RegionalExternaGateway regionalExternaGateway;

    @InjectMocks
    private SyncRegionaisUseCase useCase;

    @Test
    @DisplayName("Deve inserir nova regional da API")
    void deveInserirNovaRegionalDaAPI() {
        
        var regionaisExternas = List.of(
            new RegionalExternaDTO(1, "Regional Norte")
        );
        
        when(regionalExternaGateway.fetchRegionaisFromExternalAPI()).thenReturn(regionaisExternas);
        when(regionalGateway.findAll()).thenReturn(List.of());

        
        var output = useCase.execute();

        
        assertEquals(1, output.inseridas());
        assertEquals(0, output.inativadas());
        verify(regionalGateway).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve inativar regional ausente na API")
    void deveInativarRegionalAusenteNaAPI() {
        
        var regionalInterna = Regional.newRegional(1, "Regional Sul");
        
        when(regionalExternaGateway.fetchRegionaisFromExternalAPI()).thenReturn(List.of());
        when(regionalGateway.findAll()).thenReturn(List.of(regionalInterna));

        
        var output = useCase.execute();

        
        assertEquals(0, output.inseridas());
        assertEquals(1, output.inativadas());
        verify(regionalGateway).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve inativar e criar nova quando nome mudar (regra 3)")
    void deveInativarECriarNovaQuandoNomeMudar() {
        
        var regionalInterna = Regional.newRegional(1, "Regional Norte");
        var regionalExternaComNomeMudado = new RegionalExternaDTO(1, "Regional Centro-Norte");
        
        when(regionalExternaGateway.fetchRegionaisFromExternalAPI())
            .thenReturn(List.of(regionalExternaComNomeMudado));
        when(regionalGateway.findAll()).thenReturn(List.of(regionalInterna));

        
        var output = useCase.execute();

        
        assertEquals(0, output.inseridas());
        assertEquals(1, output.recriadas()); // Inativa antiga + cria nova
        verify(regionalGateway).saveAll(anyList());
    }

    @Test
    @DisplayName("Não deve fazer nada quando não houver mudanças")
    void naoDeveFazerNadaQuandoNaoHouverMudancas() {
        
        var regionalInterna = Regional.newRegional(1, "Regional Leste");
        var regionalExterna = new RegionalExternaDTO(1, "Regional Leste");
        
        when(regionalExternaGateway.fetchRegionaisFromExternalAPI()).thenReturn(List.of(regionalExterna));
        when(regionalGateway.findAll()).thenReturn(List.of(regionalInterna));

        
        var output = useCase.execute();

        
        assertEquals(0, output.inseridas());
        assertEquals(0, output.inativadas());
        assertEquals(0, output.recriadas());
        verify(regionalGateway, never()).saveAll(anyList());
    }
}
