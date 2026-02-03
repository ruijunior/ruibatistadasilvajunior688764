package br.com.rbsj.seplag.application.album.retrieve.list;

import br.com.rbsj.seplag.domain.album.Album;

import java.util.Set;
import java.util.stream.Collectors;

public record AlbumListOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        String urlCapa,
        Set<String> artistaIds
) {
    public static AlbumListOutput from(Album album) {
        return new AlbumListOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                album.getUrlCapa(),
                album.getArtistas().stream()
                        .map(id -> id.getValue())
                        .collect(Collectors.toSet())
        );
    }
}
