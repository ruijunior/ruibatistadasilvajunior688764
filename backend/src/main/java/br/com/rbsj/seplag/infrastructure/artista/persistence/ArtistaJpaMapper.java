package br.com.rbsj.seplag.infrastructure.artista.persistence;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.artista.TipoArtista;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtistaJpaMapper {

    @Mapping(target = "id", expression = "java(mapId(entity.getId()))")
    @Mapping(target = "tipo", expression = "java(mapTipo(entity.getTipo()))")
    Artista toDomain(ArtistaJpaEntity entity);

    @Mapping(target = "id", expression = "java(domain.getId().getValue())")
    @Mapping(target = "tipo", expression = "java(mapTipoJpa(domain.getTipo()))")
    ArtistaJpaEntity toEntity(Artista domain);

    default ArtistaID mapId(String id) {
        return ArtistaID.from(id);
    }

    default TipoArtista mapTipo(TipoArtistaJpa tipo) {
        return TipoArtista.valueOf(tipo.name());
    }

    default TipoArtistaJpa mapTipoJpa(TipoArtista tipo) {
        return TipoArtistaJpa.valueOf(tipo.name());
    }
}
