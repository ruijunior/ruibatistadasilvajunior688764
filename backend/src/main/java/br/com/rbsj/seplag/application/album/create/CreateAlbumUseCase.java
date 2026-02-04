package br.com.rbsj.seplag.application.album.create;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.notification.NotificationGateway;

import java.util.Objects;

public class CreateAlbumUseCase {

    private final AlbumGateway gateway;
    private final NotificationGateway notificationGateway;

    public CreateAlbumUseCase(AlbumGateway gateway, NotificationGateway notificationGateway) {
        this.gateway = Objects.requireNonNull(gateway);
        this.notificationGateway = Objects.requireNonNull(notificationGateway);
    }

    public CreateAlbumOutput execute(CreateAlbumCommand command) {
        var album = Album.newAlbum(command.titulo(), command.anoLancamento());
        
        command.artistas().forEach(id -> album.addArtista(ArtistaID.from(id)));
        
        album.validate();
        
        var created = this.gateway.create(album);
        
        this.notificationGateway.notifyNewAlbum(created);
        
        return CreateAlbumOutput.from(created);
    }
}
