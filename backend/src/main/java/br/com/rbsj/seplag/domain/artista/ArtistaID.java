package br.com.rbsj.seplag.domain.artista;

import br.com.rbsj.seplag.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ArtistaID extends Identifier {

    private final String value;

    private ArtistaID(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do artista não pode ser vazio");
        }
    }

    public static ArtistaID unique() {
        return new ArtistaID(UUID.randomUUID().toString().toLowerCase());
    }

    public static ArtistaID from(String id) {
        return new ArtistaID(id);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistaID artistaID = (ArtistaID) o;
        return Objects.equals(value, artistaID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
