package br.com.sidroniolima.admin.domain.category;

import br.com.sidroniolima.admin.domain.pagination.SearchQuery;
import br.com.sidroniolima.admin.domain.pagination.Pagination;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    Category create(Category aCategory);
    void deleteById(CategoryID anId);
    Optional<Category> findById(CategoryID anId);
    Category update(Category aCategory);
    Pagination<Category> findAll(SearchQuery aQuery);
    List<CategoryID> existsByIds(Iterable<CategoryID> ids);
}
