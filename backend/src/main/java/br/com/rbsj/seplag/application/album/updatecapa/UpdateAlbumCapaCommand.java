package br.com.rbsj.seplag.application.album.updatecapa;

public record UpdateAlbumCapaCommand(
        String id,
        String urlCapa
) {
    public static UpdateAlbumCapaCommand with(String id, String urlCapa) {
        return new UpdateAlbumCapaCommand(id, urlCapa);
    }
}
