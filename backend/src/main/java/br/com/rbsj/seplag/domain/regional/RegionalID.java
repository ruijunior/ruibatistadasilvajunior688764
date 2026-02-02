package br.com.rbsj.seplag.domain.regional;

import br.com.rbsj.seplag.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class RegionalID extends Identifier {

    private final String value;

    private RegionalID(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static RegionalID unique() {
        return new RegionalID(UUID.randomUUID().toString().toLowerCase());
    }

    public static RegionalID from(String id) {
        return new RegionalID(id);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionalID that = (RegionalID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
