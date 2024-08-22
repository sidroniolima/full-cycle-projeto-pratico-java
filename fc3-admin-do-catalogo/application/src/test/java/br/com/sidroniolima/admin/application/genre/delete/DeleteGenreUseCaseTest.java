package br.com.sidroniolima.admin.application.genre.delete;

import br.com.sidroniolima.admin.application.UseCaseTest;
import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.genre.GenreGateway;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGenreDeleteUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shoudDeleteGenre() {
        // given
        final var aGenre = Genre.newGenre("Ação", true);
        final var expectedId = aGenre.getId();

        Mockito.doNothing().when(genreGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        Mockito.verify(genreGateway, Mockito.times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAInvalidGenreId_whenCallsDeleteGenre_shoudDeleteGenre() {
        // given
        final var expectedId = GenreID.from("123");

        Mockito.doNothing().when(genreGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        Mockito.verify(genreGateway, Mockito.times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedException_shoudRecieveException() {
        // given
        final var expectedId = GenreID.from("123");

        Mockito.doThrow(new IllegalStateException("Gateway error")).when(genreGateway).deleteById(any());

        // when
        Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue()));

        // then
        Mockito.verify(genreGateway, Mockito.times(1)).deleteById(expectedId);
    }
}
