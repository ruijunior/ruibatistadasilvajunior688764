package br.com.rbsj.seplag.application.album.create;

public record CreateAlbumCommand(
        String titulo,
        Integer anoLancamento
) {
    public static CreateAlbumCommand with(String titulo, Integer anoLancamento) {
        return new CreateAlbumCommand(titulo, anoLancamento);
    }
}
