package br.com.rbsj.seplag.domain;

import java.time.Instant;

public abstract class Entity {

    protected final Instant criadoEm;
    protected Instant atualizadoEm;

    protected Entity(Instant criadoEm, Instant atualizadoEm) {
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    protected Entity() {
        this.criadoEm = Instant.now();
        this.atualizadoEm = Instant.now();
    }

    public abstract void validate();

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    protected void atualizarTimestamp() {
        this.atualizadoEm = Instant.now();
    }
}
