package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.validation.Error;
import br.com.sidroniolima.admin.domain.validation.ValidationHandler;
import br.com.sidroniolima.admin.domain.validation.Validator;

public class VideoValidator extends Validator {
    private static final int TITLE_MAX_LENGTH = 255;
    private static final int DESCRIPTION_MAX_LENGTH = 4_000;

    private final Video video;

    protected VideoValidator(Video video, final ValidationHandler aHandler) {
        super(aHandler);
        this.video = video;
    }

    @Override
    public void validate() {
        checkTitleConstraints();
        checkDescriptionConstraints();
        checkRatingConstraints();
        checkLaunchedAtConstraints();
    }

    private void checkTitleConstraints() {
        final var title = this.video.getTitle();

        if (title == null) {
            this.validationHandler().append(new Error("'title' should not be null"));
            return;
        }

        if (title.isBlank()) {
            this.validationHandler().append(new Error("'title' should not be empty"));
            return;
        }

        final int length = title.trim().length();

        if (length > TITLE_MAX_LENGTH) {
            this.validationHandler().append(new Error("'title' must be between 1 and 255 characters"));
        }
    }

    private void checkDescriptionConstraints() {
        final var description = this.video.getDescription();

        if (description == null) {
            this.validationHandler().append(new Error("'description' should not be null"));
            return;
        }

        if (description.isBlank()) {
            this.validationHandler().append(new Error("'description' should not be empty"));
            return;
        }

        final int length = description.trim().length();

        if (length > DESCRIPTION_MAX_LENGTH) {
            this.validationHandler().append(new Error("'description' must be between 1 and 4000 characters"));
        }
    }

    private void checkLaunchedAtConstraints() {
        final var launchedAt = this.video.getLaunchedAt();

        if (launchedAt == null) {
            this.validationHandler().append(new Error("'launchedAt' should not be null"));
        }
    }

    private void checkRatingConstraints() {
        final var rating = this.video.getRating();

        if (rating == null) {
            this.validationHandler().append(new Error("'rating' should not be null"));
        }
    }
}
