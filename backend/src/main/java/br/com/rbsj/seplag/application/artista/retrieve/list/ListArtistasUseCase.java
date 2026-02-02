package br.com.rbsj.seplag.application.artista.retrieve.list;

import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;

import java.util.Objects;

public class  ListArtistasUseCase {

    private final ArtistaGateway gateway;

    public ListArtistasUseCase(ArtistaGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    public Pagination<ArtistaListOutput> execute(ListArtistasQuery query) {
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
                        .map(ArtistaListOutput::from)
                        .toList()
        );
    }
}
