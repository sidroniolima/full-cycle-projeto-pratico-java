package br.com.sidroniolima.admin.e2e.category;

import br.com.sidroniolima.admin.E2ETest;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.e2e.MockDsl;
import br.com.sidroniolima.admin.infrastructure.category.models.UpdateCategoryRequest;
import br.com.sidroniolima.admin.infrastructure.category.persistence.CategoryRepository;
import br.com.sidroniolima.admin.infrastructure.configuration.json.Json;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
@Testcontainers
public class CategoryE2ETest implements MockDsl {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Container
    private static final MySQLContainer MYSQL_CONTAINER =
            new MySQLContainer("mysql:8.0")
                    .withPassword("123456")
                    .withUsername("root")
                    .withDatabaseName("adm_videos");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("mysql.port", () -> MYSQL_CONTAINER.getMappedPort(3306));
    }

    @Override
    public MockMvc mvc() {
        return this.mvc;
    }

    @Test
    public void testWorks() {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToUpdateACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualId = givenACategory("Movies", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;

        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsAtive);

        this.updateACategory(
                        actualId,
                        aRequestBody)
                .andExpect(status().isOk());

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsAtive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToInactivateACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;

        final var actualId = givenACategory(expectedName, expectedDescription, true);

        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsAtive);

        final var aRequest = MockMvcRequestBuilders.put("/categories/" + actualId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(aRequestBody));

        this.mvc.perform(aRequest)
                .andExpect(status().isOk());

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToActivateACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = true;

        final var actualId = givenACategory(expectedName, expectedDescription, false);

        final var aRequestBody = new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsAtive);

        final var aRequest = MockMvcRequestBuilders.put("/categories/" + actualId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(aRequestBody));

        this.mvc.perform(aRequest)
                .andExpect(status().isOk());

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToCreateANewCategoryWithValidValues() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;

        final var actualId = givenACategory(expectedName, expectedDescription, expectedIsAtive);

        final var actualCategory = retrieveACategory(actualId);

        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(expectedIsAtive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.updatedAt());
        Assertions.assertNotNull(actualCategory.createdAt());
        Assertions.assertNotNull(actualCategory.deletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToGetACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;

        final var actualId = givenACategory(expectedName, expectedDescription, expectedIsAtive);

        final var actualCategory = categoryRepository.findById(actualId.getValue()).get();

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsAtive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToSeeATreatedErrorGettingANotFoundCategory() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var aRequest = MockMvcRequestBuilders.get("/categories/123")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(aRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Category with ID 123 was not found")));
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToSearchBetweenAllCategories() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        givenACategory("Filmes", null, true);
        givenACategory("Documentários", null, true);
        givenACategory("Mistério", null, true);

        listCategories(0, 1,"mist")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.per_page", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.total", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.items[0].name", Matchers.equalTo("Mistério")))
        ;
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToSortAllCategoriesByDescriptionDesc() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        givenACategory("Filmes", "C", true);
        givenACategory("Documentários", "Z", true);
        givenACategory("Mistério", "A", true);
        givenACategory("Novela", "B", true);

        listCategories(0, 4,"", "description", "desc")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.per_page", Matchers.equalTo(4)))
                .andExpect(jsonPath("$.total", Matchers.equalTo(4)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(4)))
                .andExpect(jsonPath("$.items[0].name", Matchers.equalTo("Documentários"))) // Z
                .andExpect(jsonPath("$.items[1].name", Matchers.equalTo("Filmes"))) // C
                .andExpect(jsonPath("$.items[2].name", Matchers.equalTo("Novela"))) // B
                .andExpect(jsonPath("$.items[3].name", Matchers.equalTo("Mistério"))) // A
        ;
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToNavigateToAllCategories() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());
        Assertions.assertEquals(0, categoryRepository.count());

        givenACategory("Filmes", null, true);
        givenACategory("Documentários", null, true);
        givenACategory("Mistério", null, true);

        listCategories(0, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.per_page", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.total", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.items[0].name", Matchers.equalTo("Documentários")))
        ;

        listCategories(1, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.per_page", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.total", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.items[0].name", Matchers.equalTo("Filmes")))
        ;

        listCategories(2, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.per_page", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.total", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.items[0].name", Matchers.equalTo("Mistério")))
        ;

        listCategories(3, 1)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.per_page", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.total", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(0)))
        ;
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToDeleteACategoryByItsIdentifier() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsAtive = false;

        final var actualId = givenACategory(expectedName, expectedDescription, expectedIsAtive);

        deleteACategory(actualId)
                .andExpect(status().isNoContent());

        Assertions.assertFalse(this.categoryRepository.existsById(actualId.getValue()));
    }

    @Test
    public void asACatalogAdminIShouldNotSeeAErrorByDeletingANotExistentCategory() throws Exception {
        Assertions.assertEquals(0, categoryRepository.count());

        deleteAGenre(GenreID.from("123"))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(0, categoryRepository.count());
    }
}
