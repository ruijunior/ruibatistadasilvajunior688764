package br.com.rbsj.seplag.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface base para repositórios do domínio.
 * Define contratos que serão implementados na camada de infraestrutura.
 */
public interface BaseRepository<T, ID> {
    
    T save(T entity);
    
    Optional<T> findById(ID id);
    
    Page<T> findAll(Pageable pageable);
    
    void delete(T entity);
    
    boolean existsById(ID id);
}
