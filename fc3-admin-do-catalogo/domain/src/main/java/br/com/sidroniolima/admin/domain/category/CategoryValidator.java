package br.com.sidroniolima.admin.domain.category;

import br.com.sidroniolima.admin.domain.validation.Error;
import br.com.sidroniolima.admin.domain.validation.ValidationHandler;
import br.com.sidroniolima.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;

    private final Category category;

    public CategoryValidator(Category category, ValidationHandler aHandler) {
        super(aHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        final var name = this.category.getName();

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
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));

        }
    }
}
