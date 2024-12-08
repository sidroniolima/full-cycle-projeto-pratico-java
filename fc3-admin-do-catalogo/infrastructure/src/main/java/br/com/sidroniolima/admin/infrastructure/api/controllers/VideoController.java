package br.com.sidroniolima.admin.infrastructure.api.controllers;

import br.com.sidroniolima.admin.application.video.create.CreateVideoCommand;
import br.com.sidroniolima.admin.application.video.create.CreateVideoUseCase;
import br.com.sidroniolima.admin.application.video.retreive.get.GetVideoByIdUseCase;
import br.com.sidroniolima.admin.domain.resource.Resource;
import br.com.sidroniolima.admin.infrastructure.api.VideoAPI;
import br.com.sidroniolima.admin.infrastructure.utils.HashingUtils;
import br.com.sidroniolima.admin.infrastructure.video.models.CreateVideoRequest;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoResponse;
import br.com.sidroniolima.admin.infrastructure.video.presenters.VideoApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

@RestController
public class VideoController implements VideoAPI {

    private final CreateVideoUseCase createVideoUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;

    public VideoController(
            final CreateVideoUseCase createVideoUseCase,
            final GetVideoByIdUseCase getVideoByIdUseCase) {
        this.createVideoUseCase = Objects.requireNonNull(createVideoUseCase);
        this.getVideoByIdUseCase = Objects.requireNonNull(getVideoByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createFull(String aTitle,
        final String aDescription,
        final Integer launchedAt,
        final Double aDuration,
        final Boolean wasOpened,
        final Boolean wasPublished,
        final String aRating,
        final Set<String> categories,
        final Set<String> castMembers,
        final Set<String> genres,
        final MultipartFile videoFile,
        final MultipartFile trailerFile,
        final MultipartFile bannerFile,
        final MultipartFile thumbFile,
        final MultipartFile thumbHalfFile
    ) {

        final var aCmd = CreateVideoCommand.with(
                aTitle,
                aDescription,
                launchedAt,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                castMembers,
                categories,
                genres,
                resourceOf(videoFile),
                resourceOf(trailerFile),
                resourceOf(bannerFile),
                resourceOf(thumbFile),
                resourceOf(thumbHalfFile)
        );

        final var output = this.createVideoUseCase.execute(aCmd);

        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<?> createPartial(CreateVideoRequest payload) {
        final var aCmd = CreateVideoCommand.with(
                payload.title(),
                payload.description(),
                payload.yearLaunched(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.castMembers(),
                payload.categories(),
                payload.genres()
        );

        final var output = this.createVideoUseCase.execute(aCmd);

        return ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);
    }

    @Override
    public VideoResponse getById(String anId) {
        return VideoApiPresenter.present(this.getVideoByIdUseCase.execute(anId));
    }

    private Resource resourceOf(final MultipartFile part) {
        if (part == null) {
            return null;
        }

        try {
            return Resource.with(
                    HashingUtils.checksum(part.getBytes()),
                    part.getBytes(),
                    part.getContentType(),
                    part.getOriginalFilename()
            );
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

}
