package br.com.rbsj.seplag.application.album.update;

public record UpdateAlbumCommand(
        String id,
        String titulo,
        Integer anoLancamento
) {
    public static UpdateAlbumCommand with(String id, String titulo, Integer anoLancamento) {
        return new UpdateAlbumCommand(id, titulo, anoLancamento);
    }
}
