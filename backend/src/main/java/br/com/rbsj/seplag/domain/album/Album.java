package br.com.rbsj.seplag.domain.album;

import br.com.rbsj.seplag.domain.Entity;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class Album extends Entity {

    private final AlbumID id;
    private String titulo;
    private Integer anoLancamento;
    private String urlCapa;
    private Set<ArtistaID> artistas;

    private Album(
            AlbumID id,
            String titulo,
            Integer anoLancamento,
            String urlCapa,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        super(criadoEm, atualizadoEm);
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.urlCapa = urlCapa;
        this.artistas = new java.util.HashSet<>();
    }

    public static Album newAlbum(String titulo, Integer anoLancamento) {
        AlbumID id = AlbumID.unique();
        Instant now = Instant.now();
        return new Album(id, titulo, anoLancamento, null, now, now);
    }

    public static Album with(
            AlbumID id,
            String titulo,
            Integer anoLancamento,
            String urlCapa,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        return new Album(id, titulo, anoLancamento, urlCapa, criadoEm, atualizadoEm);
    }

    public Album update(String titulo, Integer anoLancamento) {
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.atualizarTimestamp();
        return this;
    }

    public Album updateUrlCapa(String urlCapa) {
        this.urlCapa = urlCapa;
        this.atualizarTimestamp();
        return this;
    }

    @Override
    public void validate() {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do álbum é obrigatório");
        }
    }

    public void validate(ValidationHandler handler) {
        new AlbumValidator(this, handler).validate();
    }

    public AlbumID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public String getUrlCapa() {
        return urlCapa;
    }

    public Set<ArtistaID> getArtistas() {
        return Collections.unmodifiableSet(artistas);
    }

    public void addArtista(br.com.rbsj.seplag.domain.artista.ArtistaID artistaID) {
        this.artistas.add(artistaID);
        this.atualizarTimestamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
