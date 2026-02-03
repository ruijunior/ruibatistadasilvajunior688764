package br.com.rbsj.seplag.infrastructure.album.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlbumRepository extends JpaRepository<AlbumJpaEntity, String>, JpaSpecificationExecutor<AlbumJpaEntity> {
}
