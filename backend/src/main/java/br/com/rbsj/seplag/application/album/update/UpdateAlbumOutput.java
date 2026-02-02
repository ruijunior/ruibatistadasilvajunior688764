package br.com.rbsj.seplag.application.album.update;

import br.com.rbsj.seplag.domain.album.Album;

import java.time.Instant;

public record UpdateAlbumOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        Instant atualizadoEm
) {
    public static UpdateAlbumOutput from(Album album) {
        return new UpdateAlbumOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                album.getAtualizadoEm()
        );
    }
}
