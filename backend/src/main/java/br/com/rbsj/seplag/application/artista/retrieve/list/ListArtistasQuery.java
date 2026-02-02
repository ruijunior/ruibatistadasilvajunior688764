package br.com.rbsj.seplag.application.artista.retrieve.list;

public record ListArtistasQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
    public static ListArtistasQuery with(
            int page,
            int perPage,
            String terms,
            String sort,
            String direction
    ) {
        return new ListArtistasQuery(page, perPage, terms, sort, direction);
    }
}
