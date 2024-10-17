package br.com.sidroniolima.admin.application.video.create;

import br.com.sidroniolima.admin.domain.video.Video;

public record CreateVideoOutput(String id) {
    public static CreateVideoOutput from(final Video aVideo) {
        return new CreateVideoOutput(aVideo.getId().getValue());
    }
}
