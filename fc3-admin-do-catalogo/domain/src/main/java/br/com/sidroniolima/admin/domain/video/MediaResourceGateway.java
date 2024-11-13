package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.resource.Resource;

import java.util.Optional;

public interface MediaResourceGateway {
    AudioVideoMedia storeAudioVideo(VideoID anId, VideoResource aResource);

    ImageMedia storeImage(VideoID anId, VideoResource aResource);

    Optional<Resource> getResource(VideoID anId, VideoMediaType type);

    void clearResources(VideoID anId);
}
