package br.com.rbsj.seplag.application.album.retrieve.get;

import br.com.rbsj.seplag.domain.album.Album;

import java.time.Instant;

public record GetAlbumOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        String urlCapa,
        Instant criadoEm,
        Instant atualizadoEm
) {
    public static GetAlbumOutput from(Album album) {
        return new GetAlbumOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                album.getUrlCapa(),
                album.getCriadoEm(),
                album.getAtualizadoEm()
        );
    }
}
