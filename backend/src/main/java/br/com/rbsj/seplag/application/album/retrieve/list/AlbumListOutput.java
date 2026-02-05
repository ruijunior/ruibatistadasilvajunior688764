package br.com.rbsj.seplag.application.album.retrieve.list;

import br.com.rbsj.seplag.domain.album.Album;

import java.util.Set;
import java.util.stream.Collectors;

public record AlbumListOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        Set<String> capas,
        Set<String> artistaIds
) {
    public static AlbumListOutput from(Album album, Set<String> signedCapas) {
        return new AlbumListOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                signedCapas,
                album.getArtistas().stream()
                        .map(id -> id.getValue())
                        .collect(Collectors.toSet())
        );
    }
}
