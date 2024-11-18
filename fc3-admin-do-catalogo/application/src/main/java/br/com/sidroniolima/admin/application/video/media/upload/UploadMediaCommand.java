package br.com.sidroniolima.admin.application.video.media.upload;

import br.com.sidroniolima.admin.domain.video.VideoResource;

public record UploadMediaCommand(
        String videoId,
        VideoResource videoResource
) {
    public static UploadMediaCommand with(final String videoId, final VideoResource aResource) {
        return new UploadMediaCommand(videoId, aResource);
    }
}
