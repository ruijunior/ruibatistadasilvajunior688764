package br.com.rbsj.seplag.application.album.retrieve.get;

public record GetAlbumByIdQuery(String id) {
    public static GetAlbumByIdQuery with(String id) {
        return new GetAlbumByIdQuery(id);
    }
}
