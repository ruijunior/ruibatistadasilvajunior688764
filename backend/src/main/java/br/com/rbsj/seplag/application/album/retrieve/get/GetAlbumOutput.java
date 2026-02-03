package br.com.rbsj.seplag.application.album.retrieve.get;

import br.com.rbsj.seplag.domain.album.Album;

import java.time.Instant;

public record GetAlbumOutput(
        String id,
        String titulo,
        Integer anoLancamento,
        java.util.Set<String> capas,
        Instant criadoEm,
        Instant atualizadoEm
) {
    public static GetAlbumOutput from(Album album, java.util.Set<String> signedCapas) {
        return new GetAlbumOutput(
                album.getId().getValue(),
                album.getTitulo(),
                album.getAnoLancamento(),
                signedCapas,
                album.getCriadoEm(),
                album.getAtualizadoEm()
        );
    }
}
