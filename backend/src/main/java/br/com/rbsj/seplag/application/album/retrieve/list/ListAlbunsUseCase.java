package br.com.rbsj.seplag.application.album.retrieve.list;

import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;

import java.util.Objects;

public class ListAlbunsUseCase {

    private final AlbumGateway gateway;

    public ListAlbunsUseCase(AlbumGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public Pagination<AlbumListOutput> execute(ListAlbunsQuery query) {
        var searchQuery = new SearchQuery(
                query.page(),
                query.perPage(),
                query.terms(),
                query.sort(),
                query.direction()
        );

        var pagination = this.gateway.findAll(searchQuery);

        return new Pagination<>(
                pagination.currentPage(),
                pagination.perPage(),
                pagination.total(),
                pagination.items().stream()
                        .map(AlbumListOutput::from)
                        .toList()
        );
    }
}
