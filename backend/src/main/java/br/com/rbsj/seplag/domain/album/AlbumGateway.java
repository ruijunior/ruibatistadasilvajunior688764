package br.com.rbsj.seplag.domain.album;

import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;

import java.util.Optional;

public interface AlbumGateway {

    Album create(Album album);

    Optional<Album> findById(AlbumID id);

    Album update(Album album);

    Pagination<Album> findAll(SearchQuery query);
}
