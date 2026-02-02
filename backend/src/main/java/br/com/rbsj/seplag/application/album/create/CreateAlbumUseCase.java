package br.com.rbsj.seplag.application.album.create;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumGateway;

import java.util.Objects;

public class CreateAlbumUseCase {

    private final AlbumGateway gateway;

    public CreateAlbumUseCase(AlbumGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public CreateAlbumOutput execute(CreateAlbumCommand command) {
        var album = Album.newAlbum(command.titulo(), command.anoLancamento());
        album.validate();
        
        var created = this.gateway.create(album);
        
        return CreateAlbumOutput.from(created);
    }
}
