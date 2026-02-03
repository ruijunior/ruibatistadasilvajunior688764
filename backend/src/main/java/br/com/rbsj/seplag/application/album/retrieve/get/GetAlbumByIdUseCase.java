package br.com.rbsj.seplag.application.album.retrieve.get;

import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;

import java.util.Objects;

public class GetAlbumByIdUseCase {

    private final AlbumGateway gateway;
    private final br.com.rbsj.seplag.domain.storage.StorageGateway storageGateway;

    public GetAlbumByIdUseCase(AlbumGateway gateway, br.com.rbsj.seplag.domain.storage.StorageGateway storageGateway) {
        this.gateway = Objects.requireNonNull(gateway);
        this.storageGateway = Objects.requireNonNull(storageGateway);
    }

    public GetAlbumOutput execute(GetAlbumByIdQuery query) {
        var albumId = AlbumID.from(query.id());

        var album = this.gateway.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Álbum não encontrado: " + query.id()));

        var signedCapas = album.getCapas().stream()
                .map(storageGateway::generatePresignedGetUrl)
                .collect(java.util.stream.Collectors.toSet());

        return GetAlbumOutput.from(album, signedCapas);
    }
}
