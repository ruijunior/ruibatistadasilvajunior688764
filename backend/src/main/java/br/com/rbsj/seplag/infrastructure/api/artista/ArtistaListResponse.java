package br.com.rbsj.seplag.infrastructure.api.artista;

import br.com.rbsj.seplag.application.artista.retrieve.list.ArtistaListOutput;
import br.com.rbsj.seplag.domain.artista.TipoArtista;

public record ArtistaListResponse(
        String id,
        String nome,
        TipoArtista tipo
) {
    public static ArtistaListResponse from(ArtistaListOutput output) {
        return new ArtistaListResponse(
                output.id(),
                output.nome(),
                output.tipo()
        );
    }
}
