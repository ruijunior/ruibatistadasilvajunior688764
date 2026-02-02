package br.com.rbsj.seplag.domain.artista;

import br.com.rbsj.seplag.domain.Entity;
import br.com.rbsj.seplag.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Artista extends Entity {

    private final ArtistaID id;
    private String nome;
    private TipoArtista tipo;

    private Artista(
            ArtistaID id,
            String nome,
            TipoArtista tipo,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        super(criadoEm, atualizadoEm);
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public static Artista newArtista(String nome, TipoArtista tipo) {
        ArtistaID id = ArtistaID.unique();
        Instant now = Instant.now();
        return new Artista(id, nome, tipo, now, now);
    }

    public static Artista with(
            ArtistaID id,
            String nome,
            TipoArtista tipo,
            Instant criadoEm,
            Instant atualizadoEm
    ) {
        return new Artista(id, nome, tipo, criadoEm, atualizadoEm);
    }

    public Artista update(String nome, TipoArtista tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.atualizarTimestamp();
        return this;
    }

    @Override
    public void validate() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do artista é obrigatório");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo do artista é obrigatório");
        }
    }

    public void validate(ValidationHandler handler) {
        new ArtistaValidator(this, handler).validate();
    }

    public ArtistaID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artista artista = (Artista) o;
        return Objects.equals(id, artista.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
