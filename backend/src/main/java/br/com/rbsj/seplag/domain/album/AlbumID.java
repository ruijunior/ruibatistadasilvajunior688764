package br.com.rbsj.seplag.domain.album;

import br.com.rbsj.seplag.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class AlbumID extends Identifier {

    private final String value;

    private AlbumID(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static AlbumID unique() {
        return new AlbumID(UUID.randomUUID().toString().toLowerCase());
    }

    public static AlbumID from(String id) {
        return new AlbumID(id);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumID albumID = (AlbumID) o;
        return Objects.equals(value, albumID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
