package br.com.sidroniolima.admin.application.genre.update;

import br.com.sidroniolima.admin.IntegrationTest;
import br.com.sidroniolima.admin.domain.category.Category;
import br.com.sidroniolima.admin.domain.category.CategoryGateway;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.exceptions.NotificationException;
import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.genre.GenreGateway;
import br.com.sidroniolima.admin.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@IntegrationTest
public class UpdateGenreUseCaseIT {

    @Autowired
    private UpdateGenreUseCase useCase;

    @Autowired
    private CategoryGateway categoryGateway;

    @Autowired
    private GenreGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = genreGateway.create(Genre.newGenre("acao", true));

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        final var actualGenre = genreRepository.findById(aGenre.getId().getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(
                aGenre.getCategories().size() == actualGenre.getCategories().size()
                && aGenre.getCategories().containsAll(actualGenre.getCategoryIDs())
                );
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var filmes =
                categoryGateway.create(Category.newCategory("Filmes", null, true));
        final var series = categoryGateway.create(Category.newCategory("Filmes", null, true));

        final var aGenre = genreGateway.create(
                Genre.newGenre("acao", true)
                        .addCategories(List.of(filmes.getId(), series.getId())));

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of(
                filmes.getId(),
                series.getId()
        );

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        final var actualGenre = genreRepository.findById(aGenre.getId().getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(
                aGenre.getCategories().size() == actualGenre.getCategories().size()
                        && aGenre.getCategories().containsAll(actualGenre.getCategoryIDs())
        );
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateGenre_shouldReturnNotificationException() {
        // given
        final var aGenre = genreGateway.create(Genre.newGenre("acao", true));

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateGenreAndSomeCategoryDoesNotExists_shouldReturnNotificationException() {
        // given
        final var documentarios = CategoryID.from("789");
        final var series = CategoryID.from("456");
        final var filmes = categoryGateway.create(Category.newCategory("Filmes", null, true));

        final var aGenre = genreGateway.create(Genre.newGenre("acao", true));

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId(), series, documentarios);

        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";
        final var expectedErrorCount = 2;

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );
        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());
    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = genreGateway.create(Genre.newGenre("acao", true));

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        Assertions.assertTrue(aGenre.isActive());
        Assertions.assertNull(aGenre.getDeletedAt());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        final var actualGenre = genreRepository.findById(aGenre.getId().getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(
                aGenre.getCategories().size() == actualGenre.getCategories().size()
                        && aGenre.getCategories().containsAll(actualGenre.getCategoryIDs())
        );
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.getCreatedAt());
        Assertions.assertTrue(aGenre.getUpdatedAt().isBefore(actualGenre.getUpdatedAt()));
        Assertions.assertNotNull(actualGenre.getDeletedAt());
    }

    private List<String> asString(final List<CategoryID> ids) {
        return ids
                .stream()
                .map(CategoryID::getValue)
                .toList();
    }
}
