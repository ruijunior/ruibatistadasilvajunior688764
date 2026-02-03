package br.com.rbsj.seplag.application.artista.create;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.artista.TipoArtista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do CreateArtistaUseCase")
class CreateArtistaUseCaseTest {

    @Mock
    private ArtistaGateway gateway;

    @InjectMocks
    private CreateArtistaUseCase useCase;

    @Test
    @DisplayName("Deve criar artista com dados válidos")
    void deveCriarArtistaComDadosValidos() {
        var command = CreateArtistaCommand.with("Serj Tankian", TipoArtista.CANTOR);
        var artista = Artista.newArtista("Serj Tankian", TipoArtista.CANTOR);
        
        when(gateway.create(any(Artista.class))).thenReturn(artista);

        var output = useCase.execute(command);

        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals("Serj Tankian", output.nome());
        assertEquals(TipoArtista.CANTOR, output.tipo());
        assertNotNull(output.criadoEm());
        
        verify(gateway, times(1)).create(any(Artista.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for null")
    void deveLancarExcecaoQuandoNomeNull() {
        var command = CreateArtistaCommand.with(null, TipoArtista.CANTOR);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
        
        verify(gateway, never()).create(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo for null")
    void deveLancarExcecaoQuandoTipoNull() {
        var command = CreateArtistaCommand.with("Nome Artista", null);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
        
        verify(gateway, never()).create(any());
    }

    @Test
    @DisplayName("Deve criar artista do tipo BANDA")
    void deveCriarArtistaTipoBanda() {
        var command = CreateArtistaCommand.with("Artista Test1 ", TipoArtista.BANDA);
        var artista = Artista.newArtista("Artista Test1 ", TipoArtista.BANDA);
        
        when(gateway.create(any(Artista.class))).thenReturn(artista);

        var output = useCase.execute(command);

        assertEquals(TipoArtista.BANDA, output.tipo());
        verify(gateway).create(any(Artista.class));
    }
}
