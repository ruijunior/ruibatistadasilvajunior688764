package br.com.rbsj.seplag.infrastructure.album;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumGateway;
import br.com.rbsj.seplag.domain.album.AlbumID;
import br.com.rbsj.seplag.domain.pagination.Pagination;
import br.com.rbsj.seplag.domain.pagination.SearchQuery;
import br.com.rbsj.seplag.infrastructure.album.persistence.AlbumJpaMapper;
import br.com.rbsj.seplag.infrastructure.album.persistence.AlbumRepository;
import br.com.rbsj.seplag.infrastructure.album.persistence.AlbumSpecification;
import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlbumPostgresGateway implements AlbumGateway {

    public static final String ORDER_ASC = "asc";
    private final AlbumRepository repository;
    private final ArtistaRepository artistaRepository;
    private final AlbumJpaMapper mapper;

    public AlbumPostgresGateway(
            AlbumRepository repository,
            ArtistaRepository artistaRepository,
            AlbumJpaMapper mapper
    ) {
        this.repository = repository;
        this.artistaRepository = artistaRepository;
        this.mapper = mapper;
    }

    @Override
    public Album create(Album album) {
        var entity = mapper.toEntity(album);
        
        album.getArtistas().forEach(artistaID -> {
            var artistaEntity = artistaRepository.findById(artistaID.getValue())
                    .orElseThrow(() -> new IllegalArgumentException("Artista não encontrado: " + artistaID.getValue()));
            entity.getArtistas().add(artistaEntity);
        });
        
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Album update(Album album) {
        var entity = repository.findById(album.getId().getValue())
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));

        mapper.updateEntityFromDomain(album, entity);

        // Manter artistas sincronizados
        entity.getArtistas().clear(); // Limpa e readiciona para garantir sync
        album.getArtistas().forEach(artistaID -> {
            var artistaEntity = artistaRepository.findById(artistaID.getValue())
                    .orElseThrow(() -> new IllegalArgumentException("Artista não encontrado: " + artistaID.getValue()));
            entity.getArtistas().add(artistaEntity);
        });

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
        var sort = query.direction().equalsIgnoreCase(ORDER_ASC)
                ? Sort.by(query.sort()).ascending()
                : Sort.by(query.sort()).descending();

        var pageRequest = PageRequest.of(query.page(), query.perPage(), sort);

        var spec = AlbumSpecification.withFilter(query.terms())
                .and(AlbumSpecification.withTipoArtista(query.tipoArtista()));
        
        var page = repository.findAll(spec, pageRequest);

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
