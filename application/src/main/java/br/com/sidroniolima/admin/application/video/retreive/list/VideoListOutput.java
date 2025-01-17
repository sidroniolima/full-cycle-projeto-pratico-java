package br.com.sidroniolima.admin.application.video.retreive.list;

import br.com.sidroniolima.admin.domain.video.Video;
import br.com.sidroniolima.admin.domain.video.VideoPreview;

import java.time.Instant;

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {

    public static VideoListOutput from(final VideoPreview aVideo) {
        return new VideoListOutput(
            aVideo.id(),
            aVideo.title(),
            aVideo.description(),
            aVideo.createdAt(),
            aVideo.updatedAt()
        );
    }
}
