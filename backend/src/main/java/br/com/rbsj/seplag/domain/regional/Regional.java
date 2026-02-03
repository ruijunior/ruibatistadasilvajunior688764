package br.com.rbsj.seplag.domain.regional;

import br.com.rbsj.seplag.domain.Entity;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Regional extends Entity {

    private final RegionalID id;
    private Integer idExterno;
    private String nome;
    private boolean ativo;

    private Regional(
            RegionalID id,
            Integer idExterno,
            String nome,
            boolean ativo,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        super(criadoEm, atualizadoEm);
        this.id = id;
        this.idExterno = idExterno;
        this.nome = nome;
        this.ativo = ativo;
    }

    public static Regional newRegional(Integer idExterno, String nome) {
        Instant now = Instant.now();
        return new Regional(null, idExterno, nome, true, now, now);
    }

    public static Regional with(
            RegionalID id,
            Integer idExterno,
            String nome,
            boolean ativo,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        return new Regional(id, idExterno, nome, ativo, criadoEm, atualizadoEm);
    }

    public Regional update(String nome) {
        this.nome = nome;
        this.atualizarTimestamp();
        return this;
    }

    public Regional ativar() {
        this.ativo = true;
        this.atualizarTimestamp();
        return this;
    }

    public Regional desativar() {
        this.ativo = false;
        this.atualizarTimestamp();
        return this;
    }

    @Override
    public void validate() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da regional é obrigatório");
        }
    }

    public void validate(ValidationHandler handler) {
        new RegionalValidator(this, handler).validate();
    }

    public RegionalID getId() {
        return id;
    }

    public Integer getIdExterno() {
        return idExterno;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regional regional = (Regional) o;
        return Objects.equals(id, regional.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
