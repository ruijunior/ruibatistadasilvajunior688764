package br.com.rbsj.seplag.infrastructure.api.album;

public record CreateAlbumRequest(
        String titulo,
        Integer anoLancamento
) {
}
