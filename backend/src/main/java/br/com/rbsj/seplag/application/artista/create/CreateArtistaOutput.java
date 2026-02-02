package br.com.rbsj.seplag.application.artista.create;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.TipoArtista;

import java.time.Instant;

public record CreateArtistaOutput(
        String id,
        String nome,
        TipoArtista tipo,
        Instant criadoEm
) {
    public static CreateArtistaOutput from(Artista artista) {
        return new CreateArtistaOutput(
                artista.getId().getValue(),
                artista.getNome(),
                artista.getTipo(),
                artista.getCriadoEm()
        );
    }
}
