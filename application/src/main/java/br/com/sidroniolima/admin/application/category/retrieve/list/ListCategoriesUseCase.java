package br.com.sidroniolima.admin.application.category.retrieve.list;

import br.com.sidroniolima.admin.application.UseCase;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;
import br.com.sidroniolima.admin.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}
