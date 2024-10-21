package br.com.sidroniolima.admin.application.video.delete;

import br.com.sidroniolima.admin.domain.video.VideoGateway;
import br.com.sidroniolima.admin.domain.video.VideoID;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase {
    private final VideoGateway videoGateway;

    public DefaultDeleteVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(final String anId) {
        this.videoGateway.deleteById(VideoID.from(anId));
    }
}
