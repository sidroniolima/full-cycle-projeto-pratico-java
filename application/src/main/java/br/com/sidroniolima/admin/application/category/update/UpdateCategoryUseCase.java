package br.com.sidroniolima.admin.application.category.update;

import br.com.sidroniolima.admin.application.UseCase;
import br.com.sidroniolima.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
