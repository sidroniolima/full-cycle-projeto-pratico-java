package br.com.sidroniolima.admin.application.video.update;

import br.com.sidroniolima.admin.domain.video.Video;

public record UpdateVideoOutput(String id) {
    public static UpdateVideoOutput from(final Video aVideo) {
        return new UpdateVideoOutput(aVideo.getId().getValue());
    }
}
