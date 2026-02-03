package br.com.rbsj.seplag.infrastructure.album.persistence;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumID;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlbumJpaMapper {

    default Album toDomain(AlbumJpaEntity entity) {
        var album = Album.with(
                AlbumID.from(entity.getId()),
                entity.getTitulo(),
                entity.getAnoLancamento(),
                entity.getCapas(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm()
        );
        entity.getArtistas().forEach(artista -> 
            album.addArtista(ArtistaID.from(artista.getId()))
        );
        return album;
    }

    default AlbumJpaEntity toEntity(Album domain) {
        var entity = new AlbumJpaEntity(
                domain.getId().getValue(),
                domain.getTitulo(),
                domain.getAnoLancamento(),
                domain.getCapas()
        );
        entity.setCriadoEm(domain.getCriadoEm());
        entity.setAtualizadoEm(domain.getAtualizadoEm());
        entity.setAtualizadoEm(domain.getAtualizadoEm());
        return entity;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "artistas", ignore = true)
    void updateEntityFromDomain(Album domain, @MappingTarget AlbumJpaEntity entity);
}
