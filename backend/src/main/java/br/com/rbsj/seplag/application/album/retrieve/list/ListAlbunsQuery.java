package br.com.rbsj.seplag.application.album.retrieve.list;

public record ListAlbunsQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
    public static ListAlbunsQuery with(
            int page,
            int perPage,
            String terms,
            String sort,
            String direction
    ) {
        return new ListAlbunsQuery(page, perPage, terms, sort, direction);
    }
}
