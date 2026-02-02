package br.com.rbsj.seplag.application.artista.update;

import br.com.rbsj.seplag.domain.artista.TipoArtista;

public record UpdateArtistaCommand(
        String id,
        String nome,
        TipoArtista tipo
) {
    public static UpdateArtistaCommand with(String id, String nome, TipoArtista tipo) {
        return new UpdateArtistaCommand(id, nome, tipo);
    }
}
