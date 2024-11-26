package br.com.sidroniolima.admin.application.video.retrieve.get;

import br.com.sidroniolima.admin.application.UseCaseTest;
import br.com.sidroniolima.admin.application.video.retreive.get.DefaultGetVideoByIdUseCase;
import br.com.sidroniolima.admin.domain.Fixture;
import br.com.sidroniolima.admin.domain.exceptions.NotFoundException;
import br.com.sidroniolima.admin.domain.video.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.sidroniolima.admin.domain.Fixture.Videos.audioVideo;
import static br.com.sidroniolima.admin.domain.Fixture.Videos.imageMedia;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetVideoByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetVideoByIdUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidIdn_whenCallsGetVideoById_shouldReturnVideo() {
        // given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchedYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(
                Fixture.CastMembers.wesley().getId(),
                Fixture.CastMembers.sidronio().getId()
        );

        final var expectedVideo = audioVideo(VideoMediaType.VIDEO);
        final var expectedTrailer = audioVideo(VideoMediaType.TRAILER);
        final var expectedBanner = imageMedia(VideoMediaType.BANNER);
        final var expectedThumb = imageMedia(VideoMediaType.THUMBNAIL);
        final var expectedThumbHalf = imageMedia(VideoMediaType.THUMBNAIL_HALF);

        final var aVideo = Video.newVideo(
                        expectedTitle,
                        expectedDescription,
                        expectedLaunchedYear,
                        expectedDuration,
                        expectedOpened,
                        expectedPublished,
                        expectedRating,
                        expectedCategories,
                        expectedGenres,
                        expectedMembers
                ).updateVideoMedia(expectedVideo)
                .updateTrailerMedia(expectedTrailer)
                .updateBannerMedia(expectedBanner)
                .updateThumbnailMedia(expectedThumb)
                .updateThumbnailHalfMedia(expectedThumbHalf);

        final var expectedId = aVideo.getId();

        when(videoGateway.findById(any()))
                .thenReturn(Optional.of(Video.with(aVideo)));

        // when
        final var actualVideo = this.useCase.execute(expectedId.getValue());

        // then
        Assertions.assertEquals(expectedTitle, actualVideo.title());
        Assertions.assertEquals(expectedDescription, actualVideo.description());
        Assertions.assertEquals(aVideo.getCreatedAt(), actualVideo.createdAt());
        Assertions.assertEquals(aVideo.getUpdatedAt(), actualVideo.updatedAt());
        Assertions.assertEquals(expectedLaunchedYear.getValue(), actualVideo.launchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.duration());
        Assertions.assertEquals(expectedOpened, actualVideo.opened());
        Assertions.assertEquals(expectedPublished, actualVideo.published());
        Assertions.assertEquals(expectedRating.getName(), actualVideo.rating());
        Assertions.assertEquals(asString(expectedCategories), actualVideo.categories());
        Assertions.assertEquals(asString(expectedGenres), actualVideo.genres());
        Assertions.assertEquals(asString(expectedMembers), actualVideo.castMembers());
        Assertions.assertEquals(expectedVideo, actualVideo.video());
        Assertions.assertEquals(expectedTrailer, actualVideo.trailer());
        Assertions.assertEquals(expectedBanner, actualVideo.banner());
        Assertions.assertEquals(expectedThumb, actualVideo.thumbnail());
        Assertions.assertEquals(expectedThumbHalf, actualVideo.thumbnailHalf());
    }

    @Test
    public void givenInvalidId_whenCallsGetVideoById_shouldReturnNotFound() {
        // given
        final var expectedErrorMessage = "Video with ID 123 was not found";

        final var expectedId = VideoID.from("123").getValue();

        when(videoGateway.findById(any()))
                .thenReturn(Optional.empty());

        // when
        final var actualError = Assertions.assertThrows(
                NotFoundException.class,
                () -> this.useCase.execute(expectedId));

        // then
        Assertions.assertNotNull(actualError);
        Assertions.assertEquals(expectedErrorMessage, actualError.getMessage());
    }
}
