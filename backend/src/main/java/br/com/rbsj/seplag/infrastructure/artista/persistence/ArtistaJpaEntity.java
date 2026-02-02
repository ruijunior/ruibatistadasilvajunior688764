package br.com.rbsj.seplag.infrastructure.artista.persistence;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "artistas")
public class ArtistaJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 50)
    private TipoArtistaJpa tipo;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant atualizadoEm;

    public ArtistaJpaEntity() {
    }

    public ArtistaJpaEntity(
            String id,
            String nome,
            TipoArtistaJpa tipo,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoArtistaJpa getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtistaJpa tipo) {
        this.tipo = tipo;
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
