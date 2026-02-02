package br.com.rbsj.seplag.application.artista.retrieve.list;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.TipoArtista;

public record ArtistaListOutput(
        String id,
        String nome,
        TipoArtista tipo
) {
    public static ArtistaListOutput from(Artista artista) {
        return new ArtistaListOutput(
                artista.getId().getValue(),
                artista.getNome(),
                artista.getTipo()
        );
    }
}
