package br.com.sidroniolima.admin.application.genre.retrieve.get;

import br.com.sidroniolima.admin.IntegrationTest;
import br.com.sidroniolima.admin.domain.category.Category;
import br.com.sidroniolima.admin.domain.category.CategoryGateway;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.exceptions.NotFoundException;
import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.genre.GenreGateway;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@IntegrationTest
public class GetGenreByIdUseCaseIT {

    @Autowired
    private GetGenreByIdUseCase useCase;

    @SpyBean
    private GenreGateway genreGateway;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsGetGenreById_shouldReturnGenre() {
        // given
        final var series =
                categoryGateway.create(Category.newCategory("Series", null,true));

        final var filmes =
                categoryGateway.create(Category.newCategory("Filmes", null,true));

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                series.getId(),
                filmes.getId()
        );

        final var aGenre = genreGateway.create(
                Genre.newGenre(expectedName, expectedIsActive).addCategories(expectedCategories));

        final var expectedId = aGenre.getId();

        // when
        final var actualGenre = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualGenre);
        Assertions.assertEquals(expectedId.getValue(), actualGenre.id());
        Assertions.assertEquals(expectedName, actualGenre.name());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(
                expectedCategories.size() == actualGenre.categories().size()
                && asString(expectedCategories).containsAll(actualGenre.categories())
        );
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.createdAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.updatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.deletedAt());

        Mockito.verify(genreGateway, times(1)).findById(any());
    }

    @Test
    public void givenAValidId_whenCallsGetGenreAndDoesNotExist_shouldReturnNotFound() {
        // given
        final var expectedId = GenreID.from("123");
        final var expectedErrorMessage = "Genre with ID 123 was not found";

        // when
        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(expectedId.getValue()));

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(genreGateway, times(1)).findById(any());
    }

    private List<String> asString(List<CategoryID> ids) {
        return ids
                .stream()
                .map(CategoryID::getValue)
                .toList();
    }
}
