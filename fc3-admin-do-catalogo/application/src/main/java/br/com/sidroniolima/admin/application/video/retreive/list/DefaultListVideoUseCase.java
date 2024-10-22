package br.com.sidroniolima.admin.application.video.retreive.list;

import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.video.VideoGateway;
import br.com.sidroniolima.admin.domain.video.VideoSearchQuery;

import java.util.Objects;

public class DefaultListVideoUseCase extends ListVideoUseCase {

    private final VideoGateway videoGateway;

    public DefaultListVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);    }

    @Override
    public Pagination<VideoListOutput> execute(final VideoSearchQuery aQuery) {
        return this.videoGateway.findAll(aQuery)
                .map(VideoListOutput::from);
    }
}
