package br.com.rbsj.seplag.application.artista.update;

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
@DisplayName("Testes do UpdateArtistaUseCase")
class UpdateArtistaUseCaseTest {

    @Mock
    private ArtistaGateway gateway;

    @InjectMocks
    private UpdateArtistaUseCase useCase;

    @Test
    @DisplayName("Deve atualizar artista existente")
    void deveAtualizarArtistaExistente() {
        var artistaId = ArtistaID.unique();
        var artista = Artista.newArtista("Nome Antigo", TipoArtista.CANTOR);
        var command = UpdateArtistaCommand.with(artistaId.getValue(), "Nome Novo", TipoArtista.BANDA);
        
        when(gateway.findById(any(ArtistaID.class))).thenReturn(Optional.of(artista));
        when(gateway.update(any(Artista.class))).thenReturn(artista);
        
        var output = useCase.execute(command);

        assertNotNull(output);
        assertEquals("Nome Novo", output.nome());
        assertEquals(TipoArtista.BANDA, output.tipo());
        
        verify(gateway).findById(any(ArtistaID.class));
        verify(gateway).update(any(Artista.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando artista não for encontrado")
    void deveLancarExcecaoQuandoArtistaNaoEncontrado() {
        var command = UpdateArtistaCommand.with("id-inexistente", "Nome", TipoArtista.CANTOR);

        when(gateway.findById(any(ArtistaID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
        
        verify(gateway).findById(any(ArtistaID.class));
        verify(gateway, never()).update(any());
    }

    @Test
    @DisplayName("Deve validar artista ao atualizar")
    void deveValidarArtistaAoAtualizar() {
        var artistaId = ArtistaID.unique();
        var artista = Artista.newArtista("Nome Original", TipoArtista.CANTOR);
        var command = UpdateArtistaCommand.with(artistaId.getValue(), "", TipoArtista.CANTOR);
        
        when(gateway.findById(any(ArtistaID.class))).thenReturn(Optional.of(artista));

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
        
        verify(gateway, never()).update(any());
    }
}
