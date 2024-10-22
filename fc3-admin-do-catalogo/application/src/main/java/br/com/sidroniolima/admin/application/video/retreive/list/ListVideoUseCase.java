package br.com.sidroniolima.admin.application.video.retreive.list;

import br.com.sidroniolima.admin.application.UseCase;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.video.VideoSearchQuery;

public abstract class ListVideoUseCase extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
