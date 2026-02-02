package br.com.rbsj.seplag.infrastructure.album.persistence;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "albuns")
public class AlbumJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "url_capa", length = 500)
    private String urlCapa;

    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant criadoEm;

    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant atualizadoEm;

    public AlbumJpaEntity() {
    }

    public AlbumJpaEntity(
            String id,
            String titulo,
            Integer anoLancamento,
            String urlCapa,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.urlCapa = urlCapa;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getUrlCapa() {
        return urlCapa;
    }

    public void setUrlCapa(String urlCapa) {
        this.urlCapa = urlCapa;
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
