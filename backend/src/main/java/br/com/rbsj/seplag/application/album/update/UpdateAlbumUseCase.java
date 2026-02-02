package br.com.rbsj.seplag.application.album.update;

import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;

import java.util.Objects;

public class UpdateAlbumUseCase {

    private final AlbumGateway gateway;

    public UpdateAlbumUseCase(AlbumGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public UpdateAlbumOutput execute(UpdateAlbumCommand command) {
        var albumId = AlbumID.from(command.id());
        
        var album = this.gateway.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Álbum não encontrado: " + command.id()));
        
        album.update(command.titulo(), command.anoLancamento());
        album.validate();
        
        var updated = this.gateway.update(album);
        
        return UpdateAlbumOutput.from(updated);
    }
}
