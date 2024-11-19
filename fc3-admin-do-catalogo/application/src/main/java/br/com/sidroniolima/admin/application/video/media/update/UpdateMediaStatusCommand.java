package br.com.sidroniolima.admin.application.video.media.update;

import br.com.sidroniolima.admin.domain.video.MediaStatus;

public record UpdateMediaStatusCommand(
        MediaStatus status,
        String videoId,
        String resourceId,
        String folder,
        String fileName
) {
    public static UpdateMediaStatusCommand with(
            final MediaStatus status,
            final String videoId,
            final String resourceId,
            final String folder,
            final String fileName
    ) {
        return new UpdateMediaStatusCommand(status, videoId, resourceId, folder, fileName);
    }
}
