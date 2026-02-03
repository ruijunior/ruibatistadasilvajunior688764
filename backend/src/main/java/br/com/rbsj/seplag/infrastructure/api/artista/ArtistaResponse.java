package br.com.rbsj.seplag.infrastructure.api.artista;

import br.com.rbsj.seplag.application.artista.retrieve.get.GetArtistaOutput;
import br.com.rbsj.seplag.domain.artista.TipoArtista;

import java.time.Instant;

public record ArtistaResponse(
        String id,
        String nome,
        TipoArtista tipo,
        Instant criadoEm,
        Instant atualizadoEm
) {
    public static ArtistaResponse from(GetArtistaOutput output) {
        return new ArtistaResponse(
                output.id(),
                output.nome(),
                output.tipo(),
                output.criadoEm(),
                output.atualizadoEm()
        );
    }
}
