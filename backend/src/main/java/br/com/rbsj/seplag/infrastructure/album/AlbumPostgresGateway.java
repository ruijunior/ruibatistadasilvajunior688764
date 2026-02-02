package br.com.rbsj.seplag.infrastructure.album;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;
import br.com.rbsj.seplag.infrastructure.album.persistence.AlbumJpaMapper;
import br.com.rbsj.seplag.infrastructure.album.persistence.AlbumRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlbumPostgresGateway implements AlbumGateway {

    private final AlbumRepository repository;
    private final AlbumJpaMapper mapper;

    public AlbumPostgresGateway(AlbumRepository repository, AlbumJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Album create(Album album) {
        var entity = mapper.toEntity(album);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Album update(Album album) {
        var entity = mapper.toEntity(album);
        var updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Optional<Album> findById(AlbumID id) {
        return repository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Pagination<Album> findAll(SearchQuery query) {
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
