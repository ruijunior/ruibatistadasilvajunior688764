package br.com.rbsj.seplag.domain.regional;

import br.com.rbsj.seplag.domain.Identifier;

import java.util.Objects;

public class RegionalID extends Identifier {

    private final Long value;

    private RegionalID(Long value) {
        this.value = value;
    }

    public static RegionalID from(Long id) {
        return new RegionalID(id);
    }
    
    public static RegionalID from(String id) {
        try {
            return new RegionalID(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido para Regional: " + id);
        }
    }

    public Long asLong() {
        return value;
    }

    @Override
    public String getValue() {
        return String.valueOf(value);
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
