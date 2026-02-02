package br.com.rbsj.seplag.infrastructure.regional.persistence;

import br.com.rbsj.seplag.domain.regional.Regional;
import br.com.rbsj.seplag.domain.regional.RegionalID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionalJpaMapper {

    default Regional toDomain(RegionalJpaEntity entity) {
        return Regional.with(
                RegionalID.from(entity.getId()),
                entity.getIdExterno(),
                entity.getNome(),
                entity.getAtivo(),
                entity.getCriadoEm(),
                entity.getAtualizadoEm()
        );
    }

    default RegionalJpaEntity toEntity(Regional domain) {
        return new RegionalJpaEntity(
                domain.getId().getValue(),
                domain.getIdExterno(),
                domain.getNome(),
                domain.isAtivo(),
                domain.getCriadoEm(),
                domain.getAtualizadoEm()
        );
    }
}
