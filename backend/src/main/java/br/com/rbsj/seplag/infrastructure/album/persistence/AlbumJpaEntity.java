package br.com.rbsj.seplag.infrastructure.album.persistence;

import br.com.rbsj.seplag.infrastructure.artista.persistence.ArtistaJpaEntity;
import br.com.rbsj.seplag.infrastructure.persistence.AuditableEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "album_capas", joinColumns = @JoinColumn(name = "album_id"))
    @Column(name = "url_capa")
    private Set<String> capas = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "artista_album",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    private Set<ArtistaJpaEntity> artistas = new HashSet<>();

    public AlbumJpaEntity() {
    }

    public AlbumJpaEntity(
            String id,
            String titulo,
            Integer anoLancamento,
            Set<String> capas
    ) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.capas = capas;
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

    public Set<String> getCapas() {
        return capas;
    }

    public void setCapas(Set<String> capas) {
        this.capas = capas;
    }

    public Set<ArtistaJpaEntity> getArtistas() {
        return artistas;
    }
}
