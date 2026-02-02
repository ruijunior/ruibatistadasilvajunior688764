package br.com.rbsj.seplag.application.album.retrieve.list;

import br.com.rbsj.seplag.domain.album.Album;

public record AlbumListOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        String urlCapa
) {
    public static AlbumListOutput from(Album album) {
        return new AlbumListOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                album.getUrlCapa()
        );
    }
}
