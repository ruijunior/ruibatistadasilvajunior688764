package br.com.rbsj.seplag.infrastructure.album.persistence;

import br.com.rbsj.seplag.infrastructure.persistence.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "albuns")
public class AlbumJpaEntity extends AuditableEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "url_capa", length = 500)
    private String urlCapa;

    public AlbumJpaEntity() {
    }

    public AlbumJpaEntity(
            String id,
            String titulo,
            Integer anoLancamento,
            String urlCapa
    ) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.urlCapa = urlCapa;
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
}
