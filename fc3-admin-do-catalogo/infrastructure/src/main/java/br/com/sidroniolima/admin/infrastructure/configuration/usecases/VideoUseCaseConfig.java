package br.com.sidroniolima.admin.infrastructure.configuration.usecases;

import br.com.sidroniolima.admin.application.video.media.update.DefaultUpdateMediaStatusUseCase;
import br.com.sidroniolima.admin.application.video.media.update.UpdateMediaStatusUseCase;
import br.com.sidroniolima.admin.domain.video.VideoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class VideoUseCaseConfig {

    private final VideoGateway videoGateway;

    public VideoUseCaseConfig(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Bean
    public UpdateMediaStatusUseCase updateMediaStatusUseCase() {
        return new DefaultUpdateMediaStatusUseCase(videoGateway);
    }
}
