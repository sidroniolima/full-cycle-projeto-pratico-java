package br.com.sidroniolima.admin.infrastructure.api.controllers;

import br.com.sidroniolima.admin.application.video.create.CreateVideoCommand;
import br.com.sidroniolima.admin.application.video.create.CreateVideoUseCase;
import br.com.sidroniolima.admin.application.video.delete.DeleteVideoUseCase;
import br.com.sidroniolima.admin.application.video.media.get.GetMediaCommand;
import br.com.sidroniolima.admin.application.video.media.get.GetMediaUseCase;
import br.com.sidroniolima.admin.application.video.media.upload.UploadMediaCommand;
import br.com.sidroniolima.admin.application.video.media.upload.UploadMediaUseCase;
import br.com.sidroniolima.admin.application.video.retreive.get.GetVideoByIdUseCase;
import br.com.sidroniolima.admin.application.video.retreive.list.ListVideoUseCase;
import br.com.sidroniolima.admin.application.video.update.UpdateVideoCommand;
import br.com.sidroniolima.admin.application.video.update.UpdateVideoUseCase;
import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.exceptions.NotificationException;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.resource.Resource;
import br.com.sidroniolima.admin.domain.validation.Error;
import br.com.sidroniolima.admin.domain.video.VideoMediaType;
import br.com.sidroniolima.admin.domain.video.VideoResource;
import br.com.sidroniolima.admin.domain.video.VideoSearchQuery;
import br.com.sidroniolima.admin.infrastructure.api.VideoAPI;
import br.com.sidroniolima.admin.infrastructure.utils.HashingUtils;
import br.com.sidroniolima.admin.infrastructure.video.models.CreateVideoRequest;
import br.com.sidroniolima.admin.infrastructure.video.models.UpdateVideoRequest;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoListResponse;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoResponse;
import br.com.sidroniolima.admin.infrastructure.video.presenters.VideoApiPresenter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static br.com.sidroniolima.admin.domain.utils.CollectionUtils.mapTo;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
public class VideoController implements VideoAPI {

    private final CreateVideoUseCase createVideoUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;
    private final UpdateVideoUseCase updateVideoUseCase;
    private final DeleteVideoUseCase deleteVideoUseCase;
    private final ListVideoUseCase listVideoUseCase;
    private final GetMediaUseCase getMediaUseCase;
    private final UploadMediaUseCase uploadMediaUseCase;

    public VideoController(
            final CreateVideoUseCase createVideoUseCase,
            final GetVideoByIdUseCase getVideoByIdUseCase,
            final UpdateVideoUseCase updateVideoUseCase,
            final DeleteVideoUseCase deleteVideoUseCase,
            final ListVideoUseCase listVideoUseCase,
            final GetMediaUseCase getMediaUseCase,
            final UploadMediaUseCase uploadMediaUseCase
    ) {
        this.createVideoUseCase = Objects.requireNonNull(createVideoUseCase);
        this.getVideoByIdUseCase = Objects.requireNonNull(getVideoByIdUseCase);
        this.updateVideoUseCase = Objects.requireNonNull(updateVideoUseCase);
        this.deleteVideoUseCase = Objects.requireNonNull(deleteVideoUseCase);
        this.listVideoUseCase = Objects.requireNonNull(listVideoUseCase);
        this.getMediaUseCase = Objects.requireNonNull(getMediaUseCase);
        this.uploadMediaUseCase = Objects.requireNonNull(uploadMediaUseCase);
    }

    @Override
    public Pagination<VideoListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction,
            final Set<String> castMembers,
            final Set<String> genres,
            final Set<String> categories
    ) {
        final var searchQuery =
                new VideoSearchQuery(page, perPage, search, sort, direction,
                        mapTo(castMembers, CastMemberID::from),
                        mapTo(categories, CategoryID::from),
                        mapTo(genres, GenreID::from));

        return VideoApiPresenter.present(this.listVideoUseCase.execute(searchQuery));
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

    @Override
    public ResponseEntity<?> update(final String id, final UpdateVideoRequest payload) {
        final var aCmd = UpdateVideoCommand.with(
                id,
                payload.title(),
                payload.description(),
                payload.yearLaunched(),
                payload.duration(),
                payload.opened(),
                payload.published(),
                payload.rating(),
                payload.categories(),
                payload.genres(),
                payload.castMembers()
        );

        final var output = this.updateVideoUseCase.execute(aCmd);

        return ResponseEntity
                .ok()
                .location(URI.create("/videos/" + output.id()))
                .body(VideoApiPresenter.present(output));
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteVideoUseCase.execute(anId);
    }

    @Override
    public ResponseEntity<byte[]> getMediaByType(final String id, final String type) {
        final var aMedia =
                this.getMediaUseCase.execute(GetMediaCommand.with(id, type));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(aMedia.contentType()))
                .contentLength(aMedia.content().length)
                .header(CONTENT_DISPOSITION, "attachment; filename=%s".formatted(aMedia.name()))
                .body(aMedia.content());
    }

    @Override
    public ResponseEntity<?> uploadMediaByType(
            final String id,
            final String type,
            final MultipartFile media
    ) {
        final var aType = VideoMediaType.of(type)
                .orElseThrow(() -> NotificationException.with(new Error("Invalid %s for VideoMediaType".formatted(type))));

        final var aCmd = UploadMediaCommand.with(id, VideoResource.with(aType, resourceOf(media)));

        final var output = this.uploadMediaUseCase.execute(aCmd);

        return ResponseEntity
                .created(URI.create("/videos/%s/medias/%s".formatted(id, type)))
                .body(VideoApiPresenter.present(output));
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
