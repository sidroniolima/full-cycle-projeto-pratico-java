package br.com.sidroniolima.admin.application.video.media.upload;

import br.com.sidroniolima.admin.domain.exceptions.NotFoundException;
import br.com.sidroniolima.admin.domain.video.MediaResourceGateway;
import br.com.sidroniolima.admin.domain.video.Video;
import br.com.sidroniolima.admin.domain.video.VideoGateway;
import br.com.sidroniolima.admin.domain.video.VideoID;

import java.util.Objects;

public class DefaultUploadMediaUseCase extends UploadMediaUseCase {

    private final MediaResourceGateway mediaResourceGateway;
    private final VideoGateway videoGateway;

    public DefaultUploadMediaUseCase(final MediaResourceGateway mediaResourceGateway,
                                     final VideoGateway videoGateway) {
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public UploadMediaOutput execute(final UploadMediaCommand aCmd) {
        final var anId = VideoID.from(aCmd.videoId());
        final var aResource = aCmd.videoResource();

        final var aVideo = this.videoGateway.findById(anId)
                .orElseThrow(() -> notFound(anId));

        switch (aResource.type()) {
            case VIDEO -> aVideo.updateVideoMedia(mediaResourceGateway.storeAudioVideo(anId, aResource));
            case TRAILER -> aVideo.updateTrailerMedia(mediaResourceGateway.storeAudioVideo(anId, aResource));
            case BANNER -> aVideo.updateBannerMedia(mediaResourceGateway.storeImage(anId, aResource));
            case THUMBNAIL -> aVideo.updateThumbnailMedia(mediaResourceGateway.storeImage(anId, aResource));
            case THUMBNAIL_HALF -> aVideo.updateThumbnailHalfMedia(mediaResourceGateway.storeImage(anId, aResource));
        }

        return UploadMediaOutput.with(videoGateway.update(aVideo), aResource.type());
    }

    private NotFoundException notFound(final VideoID anId) {
        return NotFoundException.with(Video.class, anId);
    }
}
