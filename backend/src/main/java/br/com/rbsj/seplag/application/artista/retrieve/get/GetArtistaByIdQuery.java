package br.com.rbsj.seplag.application.artista.retrieve.get;

public record GetArtistaByIdQuery(String id) {
    public static GetArtistaByIdQuery with(String id) {
        return new GetArtistaByIdQuery(id);
    }
}
