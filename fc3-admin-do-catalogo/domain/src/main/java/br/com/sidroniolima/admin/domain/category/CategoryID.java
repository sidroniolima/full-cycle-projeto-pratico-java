package br.com.sidroniolima.admin.domain.category;

import br.com.sidroniolima.admin.domain.Identifier;
import br.com.sidroniolima.admin.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String value;

    public CategoryID(final String value) {
        this.value =  Objects.requireNonNull(value);
    }

    public static CategoryID unique() {
        return new CategoryID(IdUtils.uuid());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryID that = (CategoryID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
