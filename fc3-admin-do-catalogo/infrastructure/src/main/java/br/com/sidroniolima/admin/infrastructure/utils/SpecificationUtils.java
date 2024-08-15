package br.com.sidroniolima.admin.infrastructure.utils;

import br.com.sidroniolima.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {
    private SpecificationUtils() {}

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get(prop)), like(term.toUpperCase()));
    }

    private static String like(String term) {
        return "%" + term + "%";
    }
}
