package br.com.rbsj.seplag.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
public abstract class AuditableEntity {

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private Instant criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant atualizadoEm;

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
