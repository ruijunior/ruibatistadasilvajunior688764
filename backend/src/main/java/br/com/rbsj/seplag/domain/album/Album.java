package br.com.rbsj.seplag.domain.album;

import br.com.rbsj.seplag.domain.Entity;
import br.com.rbsj.seplag.domain.artista.ArtistaID;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Album extends Entity {

    private final AlbumID id;
    private String titulo;
    private Integer anoLancamento;
    private Set<String> capas;
    private Set<ArtistaID> artistas;

    private Album(
            AlbumID id,
            String titulo,
            Integer anoLancamento,
            Set<String> capas,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        super(criadoEm, atualizadoEm);
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.capas = capas != null ? new HashSet<>(capas) : new HashSet<>();
        this.artistas = new HashSet<>();
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
            Set<String> capas,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        return new Album(id, titulo, anoLancamento, capas, criadoEm, atualizadoEm);
    }

    public Album update(String titulo, Integer anoLancamento) {
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.atualizarTimestamp();
        return this;
    }

    public void addCapa(String capa) {
        if (this.capas == null) {
            this.capas = new HashSet<>();
        }
        this.capas.add(capa);
        this.atualizarTimestamp();
    }

    public void removeCapa(String capa) {
        if (this.capas != null) {
            this.capas.remove(capa);
            this.atualizarTimestamp();
        }
    }

    @Override
    public void validate() {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do álbum é obrigatório");
        }
        if (titulo.length() > 200) {
            throw new IllegalArgumentException("Título do álbum não pode exceder 200 caracteres");
        }
        if (artistas == null || artistas.isEmpty()) {
            throw new IllegalArgumentException("O álbum deve ter pelo menos um artista");
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

    public Set<String> getCapas() {
        return Collections.unmodifiableSet(capas != null ? capas : Collections.emptySet());
    }

    public Set<ArtistaID> getArtistas() {
        return Collections.unmodifiableSet(artistas);
    }

    public void addArtista(ArtistaID artistaID) {
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
