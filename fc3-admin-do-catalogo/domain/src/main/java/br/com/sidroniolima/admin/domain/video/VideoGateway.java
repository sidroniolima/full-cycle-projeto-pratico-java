package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface VideoGateway {
    Video create(Video aGenre);
    void deleteById(VideoID anId);
    Optional<Genre> findById(VideoID anId);
    Video update(Video aGenre);
    Pagination<Video> findAll(VideoSearchQuery aQuery);
}
