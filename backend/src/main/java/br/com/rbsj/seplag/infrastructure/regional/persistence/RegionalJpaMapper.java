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
        String id = domain.getId() != null ? domain.getId().getValue() : null;
        var entity = new RegionalJpaEntity(
                id,
                domain.getIdExterno(),
                domain.getNome(),
                domain.isAtivo()
        );
        entity.setCriadoEm(domain.getCriadoEm());
        entity.setAtualizadoEm(domain.getAtualizadoEm());
        return entity;
    }
}
