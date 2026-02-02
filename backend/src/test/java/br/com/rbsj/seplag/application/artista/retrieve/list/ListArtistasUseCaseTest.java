package br.com.rbsj.seplag.application.artista.retrieve.list;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.artista.TipoArtista;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ListArtistasUseCase")
class ListArtistasUseCaseTest {

    @Mock
    private ArtistaGateway gateway;

    @InjectMocks
    private ListArtistasUseCase useCase;

    @Test
    @DisplayName("Deve listar artistas com paginação")
    void deveListarArtistasComPaginacao() {
        
        var artista1 = Artista.newArtista("Artista 1", TipoArtista.CANTOR);
        var artista2 = Artista.newArtista("Artista 2", TipoArtista.BANDA);
        
        var pagination = new Pagination<>(0, 10, 2, List.of(artista1, artista2));
        var query = ListArtistasQuery.with(0, 10, "", "nome", "asc");
        
        when(gateway.findAll(any(SearchQuery.class))).thenReturn(pagination);

        var output = useCase.execute(query);

        assertNotNull(output);
        assertEquals(0, output.currentPage());
        assertEquals(10, output.perPage());
        assertEquals(2, output.total());
        assertEquals(2, output.items().size());
        
        verify(gateway).findAll(any(SearchQuery.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver artistas")
    void deveRetornarListaVaziaQuandoNaoHouverArtistas() {
        var pagination = new Pagination<Artista>(0, 10, 0, List.of());
        var query = ListArtistasQuery.with(0, 10, "", "nome", "asc");
        
        when(gateway.findAll(any(SearchQuery.class))).thenReturn(pagination);
        
        var output = useCase.execute(query);

        assertEquals(0, output.total());
        assertTrue(output.items().isEmpty());
        verify(gateway).findAll(any(SearchQuery.class));
    }
}
