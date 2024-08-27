package br.com.sidroniolima.admin.domain.genre;

import br.com.sidroniolima.admin.domain.validation.Error;
import br.com.sidroniolima.admin.domain.validation.ValidationHandler;
import br.com.sidroniolima.admin.domain.validation.Validator;

public class GenreValidator extends Validator {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;

    private final Genre genre;

    protected GenreValidator(final Genre aGenre, final ValidationHandler aHandler) {
        super(aHandler);
        this.genre = aGenre;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.genre.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();

        if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 1 and 255 characters"));

        }
    }
}