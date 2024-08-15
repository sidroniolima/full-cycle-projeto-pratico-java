package br.com.sidroniolima.admin.application.category.create;

import br.com.sidroniolima.admin.application.UseCase;
import br.com.sidroniolima.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
