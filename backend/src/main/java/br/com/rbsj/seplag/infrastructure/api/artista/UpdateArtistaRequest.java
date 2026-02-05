package br.com.rbsj.seplag.infrastructure.api.artista;

import br.com.rbsj.seplag.domain.artista.TipoArtista;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateArtistaRequest(
        @NotBlank(message = "Nome do artista é obrigatório")
        String nome,

        @NotNull(message = "Tipo do artista é obrigatório (CANTOR ou BANDA)")
        TipoArtista tipo
) {
}
