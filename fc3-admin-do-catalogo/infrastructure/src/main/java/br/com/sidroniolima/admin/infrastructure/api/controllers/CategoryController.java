package br.com.sidroniolima.admin.infrastructure.api.controllers;

import br.com.sidroniolima.admin.application.category.create.CreateCategoryCommand;
import br.com.sidroniolima.admin.application.category.create.CreateCategoryOutput;
import br.com.sidroniolima.admin.application.category.create.CreateCategoryUseCase;
import br.com.sidroniolima.admin.application.category.delete.DeleteCategoryUseCase;
import br.com.sidroniolima.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import br.com.sidroniolima.admin.application.category.retrieve.list.ListCategoriesUseCase;
import br.com.sidroniolima.admin.application.category.update.UpdateCategoryCommand;
import br.com.sidroniolima.admin.application.category.update.UpdateCategoryOutput;
import br.com.sidroniolima.admin.application.category.update.UpdateCategoryUseCase;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.validation.handler.Notification;
import br.com.sidroniolima.admin.infrastructure.api.CategoryAPI;
import br.com.sidroniolima.admin.infrastructure.category.models.CategoryListResponse;
import br.com.sidroniolima.admin.infrastructure.category.models.CategoryResponse;
import br.com.sidroniolima.admin.infrastructure.category.models.CreateCategoryRequest;
import br.com.sidroniolima.admin.infrastructure.category.models.UpdateCategoryRequest;
import br.com.sidroniolima.admin.infrastructure.category.presenter.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase,
                              final GetCategoryByIdUseCase getCategoryByIdUseCase,
                              final UpdateCategoryUseCase updateCategoryUseCase,
                              final DeleteCategoryUseCase deleteCategoryUseCase,
                              final ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase, "The create category use case is necessary");
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase, "The get category use case is necessary");
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase, "The update category use case is necessary");
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase, "The delete category use case is necessary");
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase, "The list categories use case is necessary");
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryRequest input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true);

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryListResponse> listCategories(String search,
                                                           int page,
                                                           int perPage,
                                                           String sort,
                                                           String direction) {
        return listCategoriesUseCase.execute(
                new SearchQuery(page, perPage, search, sort, direction)
        ).map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(final String id) {
        //return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(id));
        //return CategoryApiPresenter.present
        //        .apply(getCategoryByIdUseCase.execute(id));
        return CategoryApiPresenter.present
                .compose(getCategoryByIdUseCase::execute)
                .apply(id);
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true);

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}

