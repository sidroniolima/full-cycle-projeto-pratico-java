package br.com.sidroniolima.admin.domain.genre;

import br.com.sidroniolima.admin.domain.Identifier;
import br.com.sidroniolima.admin.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class GenreID extends Identifier {
    private final String value;

    public GenreID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreID unique() {
        return new GenreID(IdUtils.uuid());
    }

    public static GenreID from(final String anId) {
        return new GenreID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreID that = (GenreID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
