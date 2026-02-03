package br.com.rbsj.seplag.infrastructure.api.album;

import java.util.List;

public record CreateAlbumRequest(
        String titulo,
        Integer anoLancamento,
        List<String> artistas
) {
}
