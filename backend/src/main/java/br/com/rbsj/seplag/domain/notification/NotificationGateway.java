package br.com.rbsj.seplag.domain.notification;

import br.com.rbsj.seplag.domain.album.Album;

public interface NotificationGateway {
    void notifyNewAlbum(Album album);
}
