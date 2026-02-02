package br.com.rbsj.seplag.application.album.create;

import br.com.rbsj.seplag.domain.album.Album;

import java.time.Instant;

public record CreateAlbumOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        Instant criadoEm
) {
    public static CreateAlbumOutput from(Album album) {
        return new CreateAlbumOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                album.getCriadoEm()
        );
    }
}
