package br.com.sidroniolima.admin.domain.genre;

import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {
    Genre create(Genre aGenre);
    void deleteById(GenreID anId);
    Optional<Genre> findById(GenreID anId);
    Genre update(Genre aGenre);
    Pagination<Genre> findAll(SearchQuery aQuery);
}
