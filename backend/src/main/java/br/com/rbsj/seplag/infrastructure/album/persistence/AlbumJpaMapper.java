package br.com.rbsj.seplag.infrastructure.album.persistence;

import br.com.rbsj.seplag.domain.album.Album;
import br.com.rbsj.seplag.domain.album.AlbumID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlbumJpaMapper {

    @Mapping(target = "id", expression = "java(mapId(entity.getId()))")
    Album toDomain(AlbumJpaEntity entity);

    @Mapping(target = "id", expression = "java(domain.getId().getValue())")
    AlbumJpaEntity toEntity(Album domain);

    default AlbumID mapId(String id) {
        return AlbumID.from(id);
    }
}
