package br.com.sidroniolima.admin.application.video.retrieve.list;

import br.com.sidroniolima.admin.application.Fixture;
import br.com.sidroniolima.admin.application.UseCaseTest;
import br.com.sidroniolima.admin.application.video.retreive.list.DefaultListVideoUseCase;
import br.com.sidroniolima.admin.application.video.retreive.list.VideoListOutput;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.video.Video;
import br.com.sidroniolima.admin.domain.video.VideoGateway;
import br.com.sidroniolima.admin.domain.video.VideoSearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ListVideoUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultListVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListVideo_shouldReturnVideos() {
        // given
        final var videos = List.of(
            Fixture.video(),
            Fixture.video()
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;

        final var expectedItems = videos.stream()
                .map(VideoListOutput::from)
                .toList();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                videos
        );

        when(videoGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new VideoSearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualOutput = useCase.execute(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(videoGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenCallsListVideoAndResultIsEmpty_shouldReturnGenres() {
        // given

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedItems = List.<VideoListOutput>of();

        final var expectedPagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                List.<Video>of()
        );

        Mockito.when(videoGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new VideoSearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualOutput = useCase.execute(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(videoGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenCallsListVideoAndGatewayThrowsRandomError_shouldReturnsException() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedErrorMessage = "Gateway error";

        when(videoGateway.findAll(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var aQuery = new VideoSearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualOutput = Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute(aQuery));

        // then
        Assertions.assertEquals(expectedErrorMessage, actualOutput.getMessage());

        Mockito.verify(videoGateway, times(1)).findAll(eq(aQuery));
    }
}
