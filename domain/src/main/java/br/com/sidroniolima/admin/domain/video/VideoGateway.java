package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface VideoGateway {
    Video create(Video aVideo);
    void deleteById(VideoID anId);
    Optional<Video> findById(VideoID anId);
    Video update(Video aVideo);
    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);
}
