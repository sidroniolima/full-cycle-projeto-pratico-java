package br.com.sidroniolima.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler aHandler);

    <T> T validate(Validation<T> aValidation);

    default boolean hasError() {
        return (getErrors() != null && !getErrors().isEmpty());
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

    List<Error> getErrors();

    interface Validation<T> {
        T validate();
    }
}
