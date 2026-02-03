package br.com.rbsj.seplag.application.album.create;

import java.util.Collections;
import java.util.List;

public record CreateAlbumCommand(
        String titulo,
        Integer anoLancamento,
        List<String> artistas
) {
    public static CreateAlbumCommand with(String titulo, Integer anoLancamento, List<String> artistas) {
        return new CreateAlbumCommand(titulo, anoLancamento, artistas != null ? artistas : Collections.emptyList());
    }
}
