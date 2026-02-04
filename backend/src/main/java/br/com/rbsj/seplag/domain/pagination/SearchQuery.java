package br.com.rbsj.seplag.domain.pagination;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        String tipoArtista
) {

    public SearchQuery(int page, int perPage, String terms, String sort, String direction) {
        this(page, perPage, terms, sort, direction, null);
    }
}
