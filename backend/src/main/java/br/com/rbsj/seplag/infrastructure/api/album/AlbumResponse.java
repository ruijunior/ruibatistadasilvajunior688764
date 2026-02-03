package br.com.rbsj.seplag.infrastructure.api.album;

import br.com.rbsj.seplag.application.album.retrieve.get.GetAlbumOutput;

import java.time.Instant;

public record AlbumResponse(
        String id,
        String titulo,
        Integer anoLancamento,
        String urlCapa,
        Instant criadoEm,
        Instant atualizadoEm
) {
    public static AlbumResponse from(GetAlbumOutput output) {
        return new AlbumResponse(
                output.id(),
                output.titulo(),
                output.anoLancamento(),
                output.urlCapa(),
                output.criadoEm(),
                output.atualizadoEm()
        );
    }
}
