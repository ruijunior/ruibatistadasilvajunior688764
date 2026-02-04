package br.com.rbsj.seplag.application.album.retrieve.list;

import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;
import br.com.rbsj.seplag.domain.storage.StorageGateway;

import java.util.Objects;
import java.util.stream.Collectors;

public class ListAlbunsUseCase {

    private final AlbumGateway gateway;
    private final StorageGateway storageGateway;

    public ListAlbunsUseCase(AlbumGateway gateway, StorageGateway storageGateway) {
        this.gateway = Objects.requireNonNull(gateway);
        this.storageGateway = Objects.requireNonNull(storageGateway);
    }

    public Pagination<AlbumListOutput> execute(ListAlbunsQuery query) {
        var searchQuery = new SearchQuery(
                query.page(),
                query.perPage(),
                query.terms(),
                query.sort(),
                query.direction(),
                query.tipoArtista()
        );

        var pagination = this.gateway.findAll(searchQuery);

        return new Pagination<>(
                pagination.currentPage(),
                pagination.perPage(),
                pagination.total(),
                pagination.items().stream()
                        .map(album -> {
                            var signedCapas = album.getCapas().stream()
                                    .map(storageGateway::generatePresignedGetUrl)
                                    .collect(Collectors.toSet());
                            return AlbumListOutput.from(album, signedCapas);
                        })
                        .toList()
        );
    }
}
