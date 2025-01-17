package br.com.sidroniolima.admin.application.genre.retrieve.list;

import br.com.sidroniolima.admin.application.UseCase;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}
