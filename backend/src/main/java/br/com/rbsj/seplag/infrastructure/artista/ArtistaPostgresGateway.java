package br.com.rbsj.seplag.infrastructure.artista;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaGateway;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;
import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaJpaMapper;
import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArtistaPostgresGateway implements ArtistaGateway {

    private final ArtistaRepository repository;
    private final ArtistaJpaMapper mapper;

    public ArtistaPostgresGateway(ArtistaRepository repository, ArtistaJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Artista create(Artista artista) {
        var entity = mapper.toEntity(artista);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Artista update(Artista artista) {
        var entity = mapper.toEntity(artista);
        var updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Optional<Artista> findById(ArtistaID id) {
        return repository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Pagination<Artista> findAll(SearchQuery query) {
        var sort = query.direction().equalsIgnoreCase("asc")
                ? Sort.by(query.sort()).ascending()
                : Sort.by(query.sort()).descending();

        var pageRequest = PageRequest.of(query.page(), query.perPage(), sort);
        var page = repository.findAll(pageRequest);

        return new Pagination<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getContent().stream()
                        .map(mapper::toDomain)
                        .toList()
        );
    }
}
