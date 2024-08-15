package br.com.sidroniolima.admin.infrastructure.category.presenter;

import br.com.sidroniolima.admin.application.category.retrieve.get.GetCategoryByIdOutput;
import br.com.sidroniolima.admin.application.category.retrieve.list.CategoryListOutput;
import br.com.sidroniolima.admin.infrastructure.category.models.CategoryResponse;
import br.com.sidroniolima.admin.infrastructure.category.models.CategoryListResponse;

import java.util.function.Function;

public interface CategoryApiPresenter {

    Function<GetCategoryByIdOutput, CategoryResponse> present =
            output -> new CategoryResponse(
                    output.id().getValue(),
                    output.name(),
                    output.description(),
                    output.isActive(),
                    output.createdAt(),
                    output.updatedAt(),
                    output.deletedAt()
            );

    static CategoryResponse present(final GetCategoryByIdOutput output) {
        return new CategoryResponse(
          output.id().getValue(),
          output.name(),
          output.description(),
          output.isActive(),
          output.createdAt(),
          output.updatedAt(),
          output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
