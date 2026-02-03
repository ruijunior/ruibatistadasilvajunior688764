package br.com.rbsj.seplag.application.artista.retrieve.get;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.artista.TipoArtista;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do GetArtistaByIdUseCase")
class GetArtistaByIdUseCaseTest {

    @Mock
    private ArtistaGateway gateway;

    @InjectMocks
    private GetArtistaByIdUseCase useCase;

    @Test
    @DisplayName("Deve buscar artista por ID")
    void deveBuscarArtistaPorId() {
        var artistaId = ArtistaID.unique();
        var artista = Artista.newArtista("Artista Test1 ", TipoArtista.BANDA);
        var query = GetArtistaByIdQuery.with(artistaId.getValue());
        
        when(gateway.findById(any(ArtistaID.class))).thenReturn(Optional.of(artista));

        var output = useCase.execute(query);

        assertNotNull(output);
        assertEquals("Artista Test1 ", output.nome());
        assertEquals(TipoArtista.BANDA, output.tipo());
        assertNotNull(output.criadoEm());
        assertNotNull(output.atualizadoEm());
        
        verify(gateway).findById(any(ArtistaID.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando artista não existir")
    void deveLancarExcecaoQuandoArtistaNaoExistir() {
        var query = GetArtistaByIdQuery.with("id-inexistente");
        
        when(gateway.findById(any(ArtistaID.class))).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, 
            () -> useCase.execute(query));
        
        assertTrue(exception.getMessage().contains("não encontrado"));
        verify(gateway).findById(any(ArtistaID.class));
    }
}
