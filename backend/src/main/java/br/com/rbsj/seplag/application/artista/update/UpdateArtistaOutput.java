package br.com.rbsj.seplag.application.artista.update;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.TipoArtista;

import java.time.Instant;

public record UpdateArtistaOutput(
        String id,
        String nome,
        TipoArtista tipo,
        Instant atualizadoEm
) {
    public static UpdateArtistaOutput from(Artista artista) {
        return new UpdateArtistaOutput(
                artista.getId().getValue(),
                artista.getNome(),
                artista.getTipo(),
                artista.getAtualizadoEm()
        );
    }
}
