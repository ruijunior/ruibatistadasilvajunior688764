package br.com.rbsj.seplag.infrastructure.album.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<AlbumJpaEntity, String> {
}
