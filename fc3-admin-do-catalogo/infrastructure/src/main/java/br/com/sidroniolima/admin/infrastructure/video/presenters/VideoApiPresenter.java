package br.com.sidroniolima.admin.infrastructure.video.presenters;

import br.com.sidroniolima.admin.application.video.media.upload.UploadMediaOutput;
import br.com.sidroniolima.admin.application.video.retreive.get.VideoOutput;
import br.com.sidroniolima.admin.application.video.retreive.list.VideoListOutput;
import br.com.sidroniolima.admin.application.video.update.UpdateVideoOutput;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.video.AudioVideoMedia;
import br.com.sidroniolima.admin.domain.video.ImageMedia;
import br.com.sidroniolima.admin.infrastructure.video.models.*;

public interface VideoApiPresenter {
    static VideoResponse present(final VideoOutput output) {
        return new VideoResponse(
                output.id(),
                output.title(),
                output.description(),
                output.launchedAt(),
                output.duration(),
                output.opened(),
                output.published(),
                output.rating().getName(),
                output.createdAt(),
                output.updatedAt(),
                present(output.banner()),
                present(output.thumbnail()),
                present(output.thumbnailHalf()),
                present(output.video()),
                present(output.trailer()),
                output.categories(),
                output.genres(),
                output.castMembers()
        );
    }

    static AudioVideoMediaResponse present (final AudioVideoMedia media) {
        if (media == null) {
            return null;
        }

        return new AudioVideoMediaResponse(
                media.id(),
                media.checksum(),
                media.name(),
                media.rawLocation(),
                media.encodedLocation(),
                media.status().name()
        );
    }

    static ImageMediaResponse present (final ImageMedia image) {
        if (image == null) {
            return null;
        }

        return new ImageMediaResponse(
                image.id(),
                image.checksum(),
                image.name(),
                image.location()
        );
    }

    static UpdateVideoResponse present(final UpdateVideoOutput output) {
        return new UpdateVideoResponse(output.id());
    }

    static VideoListResponse present(final VideoListOutput output) {
        return new VideoListResponse(
                output.id(),
                output.title(),
                output.description(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static Pagination<VideoListResponse> present(final Pagination<VideoListOutput> page) {
        return page.map(VideoApiPresenter::present);
    }

    static UploadMediaResponse present(final UploadMediaOutput output) {
        return new UploadMediaResponse(output.videoId(), output.mediaType());
    }
}
