package br.com.rbsj.seplag.infrastructure.api.album;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateAlbumRequest(
        @NotBlank(message = "Título é obrigatório")
        String titulo,

        @NotNull(message = "Ano de lançamento é obrigatório")
        @Max(value = 9999, message = "Ano inválido")
        Integer anoLancamento,

        @NotEmpty(message = "Pelo menos um artista é obrigatório")
        List<String> artistas
) {
}
