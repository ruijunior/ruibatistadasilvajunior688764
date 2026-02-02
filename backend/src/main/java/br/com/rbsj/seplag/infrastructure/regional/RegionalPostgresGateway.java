package br.com.rbsj.seplag.infrastructure.regional;

import br.com.rbsj.seplag.domain.regional.Regional;
import br.com.rbsj.seplag.domain.regional.RegionalGateway;
import br.com.rbsj.seplag.domain.regional.RegionalID;
import br.com.rbsj.seplag.infrastructure.regional.persistence.RegionalJpaMapper;
import br.com.rbsj.seplag.infrastructure.regional.persistence.RegionalRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RegionalPostgresGateway implements RegionalGateway {

    private final RegionalRepository repository;
    private final RegionalJpaMapper mapper;

    public RegionalPostgresGateway(RegionalRepository repository, RegionalJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Regional create(Regional regional) {
        var entity = mapper.toEntity(regional);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Regional update(Regional regional) {
        var entity = mapper.toEntity(regional);
        var updated = repository.save(entity);
        return mapper.toDomain(updated);
    }

    @Override
    public Regional findByNome(String nome) {
        return repository.findByNome(nome)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Regional com nome " + nome + " não encontrado"));
    }

    @Override
    public Regional findByIdExterno(Integer idExterno) {
        return repository.findByIdExterno(idExterno)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Regional com idExterno " + idExterno + " não encontrado"));
    }

    @Override
    public List<Regional> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<Regional> regionais) {
        var entities = regionais.stream()
                .map(mapper::toEntity)
                .toList();
        repository.saveAll(entities);
    }
}
