package br.com.rbsj.seplag.domain.entity;

/**
 * Classe base para entidades do domínio.
 * Todas as entidades devem estender esta classe.
 */
public abstract class BaseEntity {
    
    public abstract Long getId();
    
    public abstract void validate();
}
