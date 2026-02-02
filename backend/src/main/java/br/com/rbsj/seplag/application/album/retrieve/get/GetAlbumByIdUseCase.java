package br.com.rbsj.seplag.application.album.retrieve.get;

import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;

import java.util.Objects;

public class GetAlbumByIdUseCase {

    private final AlbumGateway gateway;

    public GetAlbumByIdUseCase(AlbumGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public GetAlbumOutput execute(GetAlbumByIdQuery query) {
        var albumId = AlbumID.from(query.id());

        var album = this.gateway.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Álbum não encontrado: " + query.id()));

        return GetAlbumOutput.from(album);
    }
}
