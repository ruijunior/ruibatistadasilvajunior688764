package br.com.rbsj.seplag.infrastructure.regional.persistence;

import br.com.rbsj.seplag.domain.regional.Regional;
import br.com.rbsj.seplag.domain.regional.RegionalID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegionalJpaMapper {

    @Mapping(target = "id", expression = "java(mapId(entity.getId()))")
    @Mapping(target = "ativo", source = "ativo")
    Regional toDomain(RegionalJpaEntity entity);

    @Mapping(target = "id", expression = "java(domain.getId().getValue())")
    @Mapping(target = "ativo", source = "ativo")
    RegionalJpaEntity toEntity(Regional domain);

    default RegionalID mapId(String id) {
        return RegionalID.from(id);
    }
}
