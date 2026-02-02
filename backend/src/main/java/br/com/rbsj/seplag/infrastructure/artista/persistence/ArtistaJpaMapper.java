package br.com.rbsj.seplag.infrastructure.artista.persistence;

import br.com.rbsj.seplag.domain.artista.Artista;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.artista.TipoArtista;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistaJpaMapper {

    default Artista toDomain(ArtistaJpaEntity entity) {
        return Artista.with(
                ArtistaID.from(entity.getId()),
                entity.getNome(),
                TipoArtista.valueOf(entity.getTipo().name()),
                entity.getCriadoEm(),
                entity.getAtualizadoEm()
        );
    }

    default ArtistaJpaEntity toEntity(Artista domain) {
        return new ArtistaJpaEntity(
                domain.getId().getValue(),
                domain.getNome(),
                TipoArtistaJpa.valueOf(domain.getTipo().name()),
                domain.getCriadoEm(),
                domain.getAtualizadoEm()
        );
    }
}
