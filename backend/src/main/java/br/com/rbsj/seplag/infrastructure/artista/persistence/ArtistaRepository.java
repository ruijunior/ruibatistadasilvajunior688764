package br.com.rbsj.seplag.infrastructure.artista.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<ArtistaJpaEntity, String> {
}
