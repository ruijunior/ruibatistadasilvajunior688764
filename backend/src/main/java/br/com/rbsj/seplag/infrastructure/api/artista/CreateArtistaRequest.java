package br.com.rbsj.seplag.infrastructure.api.artista;

import br.com.rbsj.seplag.domain.artista.TipoArtista;

public record CreateArtistaRequest(
        String nome,
        TipoArtista tipo
) {
}
