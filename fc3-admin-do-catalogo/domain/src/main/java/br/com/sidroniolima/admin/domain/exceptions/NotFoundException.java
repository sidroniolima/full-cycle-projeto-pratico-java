package br.com.sidroniolima.admin.domain.exceptions;

import br.com.sidroniolima.admin.domain.AggregateRoot;
import br.com.sidroniolima.admin.domain.Identifier;
import br.com.sidroniolima.admin.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(final Class<? extends AggregateRoot<?>> anAgregate,
                                         final Identifier id) {
        final var anError = "%s with ID %s was not found"
                .formatted(anAgregate.getSimpleName(), id.getValue());

        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.message(), List.of(error));
    }
}
