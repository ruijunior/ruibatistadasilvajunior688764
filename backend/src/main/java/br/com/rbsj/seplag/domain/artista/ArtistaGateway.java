package br.com.rbsj.seplag.domain.artista;

import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;

import java.util.Optional;

public interface ArtistaGateway {

    Artista create(Artista artista);

    void deleteById(ArtistaID id);

    Optional<Artista> findById(ArtistaID id);

    Artista update(Artista artista);

    Pagination<Artista> findAll(SearchQuery query);
}
