package br.com.rbsj.seplag.infrastructure.regional.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionalRepository extends JpaRepository<RegionalJpaEntity, String> {
    
    Optional<RegionalJpaEntity> findByNome(String nome);
    
    Optional<RegionalJpaEntity> findByIdExterno(Integer idExterno);
}
