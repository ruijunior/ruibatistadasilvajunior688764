package br.com.rbsj.seplag.application.artista.create;

import br.com.rbsj.seplag.domain.artista.TipoArtista;

public record CreateArtistaCommand(
        String nome,
        TipoArtista tipo
) {
    public static CreateArtistaCommand with(String nome, TipoArtista tipo) {
        return new CreateArtistaCommand(nome, tipo);
    }
}
