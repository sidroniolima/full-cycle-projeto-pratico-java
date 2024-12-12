package br.com.sidroniolima.admin.infrastructure.configuration.usecases;

import br.com.sidroniolima.admin.application.video.create.CreateVideoUseCase;
import br.com.sidroniolima.admin.application.video.create.DefaultCreateVideoUseCase;
import br.com.sidroniolima.admin.application.video.delete.DefaultDeleteVideoUseCase;
import br.com.sidroniolima.admin.application.video.delete.DeleteVideoUseCase;
import br.com.sidroniolima.admin.application.video.media.get.DefaultGetMediaUseCase;
import br.com.sidroniolima.admin.application.video.media.get.GetMediaUseCase;
import br.com.sidroniolima.admin.application.video.media.update.DefaultUpdateMediaStatusUseCase;
import br.com.sidroniolima.admin.application.video.media.update.UpdateMediaStatusUseCase;
import br.com.sidroniolima.admin.application.video.media.upload.DefaultUploadMediaUseCase;
import br.com.sidroniolima.admin.application.video.media.upload.UploadMediaUseCase;
import br.com.sidroniolima.admin.application.video.retreive.get.DefaultGetVideoByIdUseCase;
import br.com.sidroniolima.admin.application.video.retreive.get.GetVideoByIdUseCase;
import br.com.sidroniolima.admin.application.video.retreive.list.DefaultListVideoUseCase;
import br.com.sidroniolima.admin.application.video.retreive.list.ListVideoUseCase;
import br.com.sidroniolima.admin.application.video.update.DefaultUpdateVideoUseCase;
import br.com.sidroniolima.admin.application.video.update.UpdateVideoUseCase;
import br.com.sidroniolima.admin.domain.castmember.CastMemberGateway;
import br.com.sidroniolima.admin.domain.category.CategoryGateway;
import br.com.sidroniolima.admin.domain.genre.GenreGateway;
import br.com.sidroniolima.admin.domain.video.MediaResourceGateway;
import br.com.sidroniolima.admin.domain.video.VideoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class VideoUseCaseConfig {

    private final CategoryGateway categoryGateway;
    private final CastMemberGateway castMemberGateway;
    private final GenreGateway genreGateway;
    private final MediaResourceGateway mediaResourceGateway;
    private final VideoGateway videoGateway;

    public VideoUseCaseConfig(
            final CategoryGateway categoryGateway,
            final CastMemberGateway castMemberGateway,
            final GenreGateway genreGateway,
            final MediaResourceGateway mediaResourceGateway,
            final VideoGateway videoGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Bean
    public CreateVideoUseCase createVideoUseCase() {
        return new DefaultCreateVideoUseCase(categoryGateway, genreGateway, castMemberGateway, videoGateway, mediaResourceGateway);
    }

    @Bean
    public UpdateVideoUseCase updateVideoUseCase() {
        return new DefaultUpdateVideoUseCase(categoryGateway, genreGateway, castMemberGateway, videoGateway, mediaResourceGateway);
    }

    @Bean
    public GetVideoByIdUseCase getVideoByIdUseCase() {
        return new DefaultGetVideoByIdUseCase(videoGateway);
    }

    @Bean
    public DeleteVideoUseCase deleteVideoUseCase() {
        return new DefaultDeleteVideoUseCase(videoGateway, mediaResourceGateway);
    }

    @Bean
    public ListVideoUseCase listVideosUseCase() {
        return new DefaultListVideoUseCase(videoGateway);
    }

    @Bean
    public GetMediaUseCase getMediaUseCase() {
        return new DefaultGetMediaUseCase(mediaResourceGateway);
    }

    @Bean
    public UploadMediaUseCase uploadMediaUseCase() {
        return new DefaultUploadMediaUseCase(mediaResourceGateway, videoGateway);
    }

    @Bean
    public UpdateMediaStatusUseCase updateMediaStatusUseCase() {
        return new DefaultUpdateMediaStatusUseCase(videoGateway);
    }
}
