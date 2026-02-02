package br.com.rbsj.seplag.infrastructure.regional.persistence;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "regionais")
public class RegionalJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "id_externo")
    private Integer idExterno;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant atualizadoEm;

    public RegionalJpaEntity() {
    }

    public RegionalJpaEntity(
            String id,
            Integer idExterno,
            String nome,
            Boolean ativo,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        this.id = id;
        this.idExterno = idExterno;
        this.nome = nome;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(Integer idExterno) {
        this.idExterno = idExterno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

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
