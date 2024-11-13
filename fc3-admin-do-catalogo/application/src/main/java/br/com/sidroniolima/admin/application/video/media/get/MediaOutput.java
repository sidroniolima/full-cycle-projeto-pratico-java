package br.com.sidroniolima.admin.application.video.media.get;

import br.com.sidroniolima.admin.domain.resource.Resource;

public record MediaOutput(
        byte[] content,
        String contentType,
        String name
) {
    public static MediaOutput with(final Resource aResource) {
        return new MediaOutput(aResource.content(), aResource.contentType(), aResource.name());
    }
}
