package br.com.rbsj.seplag.application.artista.retrieve.get;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.TipoArtista;

import java.time.Instant;

public record GetArtistaOutput(
        String id,
        String nome,
        TipoArtista tipo,
        Instant criadoEm,
        Instant atualizadoEm
) {
    public static GetArtistaOutput from(Artista artista) {
        return new GetArtistaOutput(
                artista.getId().getValue(),
                artista.getNome(),
                artista.getTipo(),
                artista.getCriadoEm(),
                artista.getAtualizadoEm()
        );
    }
}
