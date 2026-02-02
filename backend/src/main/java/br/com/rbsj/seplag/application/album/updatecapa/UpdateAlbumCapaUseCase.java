package br.com.rbsj.seplag.application.album.updatecapa;

import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;

import java.util.Objects;

public class UpdateAlbumCapaUseCase {

    private final AlbumGateway gateway;

    public UpdateAlbumCapaUseCase(AlbumGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public void execute(UpdateAlbumCapaCommand command) {
        var albumId = AlbumID.from(command.id());

        var album = this.gateway.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Álbum não encontrado: " + command.id()));

        album.updateUrlCapa(command.urlCapa());

        this.gateway.update(album);
    }
}
