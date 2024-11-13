package br.com.sidroniolima.admin.infrastructure.video;

import br.com.sidroniolima.admin.domain.resource.Resource;
import br.com.sidroniolima.admin.domain.video.*;
import br.com.sidroniolima.admin.infrastructure.configuration.properties.storage.StorageProperties;
import br.com.sidroniolima.admin.infrastructure.services.StorageService;
import org.springframework.stereotype.Component;

@Component
public class DefaultMediaResourceGateway implements MediaResourceGateway {

    private final String fileNamePattern;
    private final String locationPattern;
    private final StorageService storageService;

    public DefaultMediaResourceGateway(final StorageProperties props, final StorageService storageService) {
        this.fileNamePattern = props.getFileNamePattern();
        this.locationPattern = props.getLocationPattern();
        this.storageService = storageService;
    }

    @Override
    public AudioVideoMedia storeAudioVideo(final VideoID anId, final VideoResource videoResource) {
        final var filepath = filePath(anId, videoResource);
        final var aResource = videoResource.resource();

        store(filepath, aResource);

        return AudioVideoMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public ImageMedia storeImage(final VideoID anId, final VideoResource videoResource) {
        final var filepath = filePath(anId, videoResource);
        final var aResource = videoResource.resource();

        store(filepath, aResource);

        return ImageMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public void clearResources(final VideoID anId) {
        final var ids = this.storageService.list(folder(anId));
        this.storageService.deleteAll(ids);
    }

    private String fileName(final VideoMediaType aType) {
        return fileNamePattern.replace("{type}", aType.name());
    }

    private String folder(final VideoID anId) {
        return locationPattern.replace("{videoId}", anId.getValue());
    }

    private String filePath(final VideoID anId, final VideoResource aResource) {
        return folder(anId)
                .concat("/")
                .concat(fileName(aResource.type())
                );
    }

    private void store(final String filepath, final Resource aResource) {
        this.storageService.store(filepath, aResource);
    }
}
