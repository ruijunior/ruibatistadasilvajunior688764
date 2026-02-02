package br.com.rbsj.seplag.application.album.create;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumGateway;
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
@DisplayName("Testes do CreateAlbumUseCase")
class CreateAlbumUseCaseTest {

    @Mock
    private AlbumGateway gateway;

    @InjectMocks
    private CreateAlbumUseCase useCase;

    @Test
    @DisplayName("Deve criar album com dados válidos")
    void deveCriarAlbumComDadosValidos() {
        var command = CreateAlbumCommand.with("Toxicity", 2001);
        var album = Album.newAlbum("Toxicity", 2001);
        
        when(gateway.create(any(Album.class))).thenReturn(album);
        
        var output = useCase.execute(command);

        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals("Toxicity", output.titulo());
        assertEquals(2001, output.anoLancamento());
        assertNotNull(output.criadoEm());
        
        verify(gateway, times(1)).create(any(Album.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando titulo for null")
    void deveLancarExcecaoQuandoTituloNull() {
        var command = CreateAlbumCommand.with(null, 2000);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
        
        verify(gateway, never()).create(any());
    }
}
