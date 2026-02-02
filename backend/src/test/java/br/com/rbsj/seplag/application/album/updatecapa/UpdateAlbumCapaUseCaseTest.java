package br.com.rbsj.seplag.application.album.updatecapa;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UpdateAlbumCapaUseCase")
class UpdateAlbumCapaUseCaseTest {

    @Mock
    private AlbumGateway gateway;

    @InjectMocks
    private UpdateAlbumCapaUseCase useCase;

    @Test
    @DisplayName("Deve atualizar URL da capa após upload no MinIO")
    void deveAtualizarUrlCapaAposUpload() {
        var album = Album.newAlbum("Album Teste", 2020);
        var command = UpdateAlbumCapaCommand.with(album.getId().getValue(), "https://minio.example.com/capas/album-123.jpg");
        
        when(gateway.findById(any(AlbumID.class))).thenReturn(Optional.of(album));
        when(gateway.update(any(Album.class))).thenReturn(album);

        useCase.execute(command);

        verify(gateway).findById(any(AlbumID.class));
        verify(gateway).update(any(Album.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando album não existir")
    void deveLancarExcecaoQuandoAlbumNaoExistir() {
        var command = UpdateAlbumCapaCommand.with("id-inexistente", "url");
        
        when(gateway.findById(any(AlbumID.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(command));
        
        verify(gateway, never()).update(any());
    }
}
