package br.com.rbsj.seplag.infrastructure.album.persistence;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumJpaMapper {

    default Album toDomain(AlbumJpaEntity entity) {
        return Album.with(
                AlbumID.from(entity.getId()),
                entity.getTitulo(),
                entity.getAnoLancamento(),
                entity.getUrlCapa(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm()
        );
    }

    default AlbumJpaEntity toEntity(Album domain) {
        return new AlbumJpaEntity(
                domain.getId().getValue(),
                domain.getTitulo(),
                domain.getAnoLancamento(),
                domain.getUrlCapa(),
                domain.getCriadoEm(),
                domain.getAtualizadoEm()
        );
    }
}
