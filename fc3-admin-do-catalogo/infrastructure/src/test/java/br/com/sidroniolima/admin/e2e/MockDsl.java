package br.com.sidroniolima.admin.e2e;

import br.com.sidroniolima.admin.domain.Identifier;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.infrastructure.category.models.CategoryResponse;
import br.com.sidroniolima.admin.infrastructure.category.models.CreateCategoryRequest;
import br.com.sidroniolima.admin.infrastructure.category.models.UpdateCategoryRequest;
import br.com.sidroniolima.admin.infrastructure.configuration.json.Json;
import br.com.sidroniolima.admin.infrastructure.genre.models.CreateGenreRequest;
import br.com.sidroniolima.admin.infrastructure.genre.models.GenreResponse;
import br.com.sidroniolima.admin.infrastructure.genre.models.UpdateGenreRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.function.Function;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    default ResultActions deleteACategory(final Identifier anId) throws Exception {
        return this.delete("/categories/", anId);
    }

    default CategoryID givenACategory(final String aName, final String aDescription, final boolean isActive) throws Exception {
        final var aRequestBody = new CreateCategoryRequest(aName, aDescription,isActive);
        final var actualId = this.given("/categories", aRequestBody);
        return CategoryID.from(actualId);
    }

    default ResultActions listCategories(final int page, final int perPage, String search) throws Exception {
        return listCategories(page, perPage, search, "", "");
    }

    default ResultActions listCategories(final int page, final int perPage) throws Exception {
        return listCategories(page, perPage, "", "", "");
    }

    default ResultActions listCategories(final int page,
                                         final int perPage,
                                         final String search,
                                         final String sort,
                                         final String direction) throws Exception {
        return this.list("/categories",
                page,
                perPage,
                search,
                sort,
                direction);
    }

    default CategoryResponse retrieveACategory(final Identifier anId) throws Exception {
        return this.retrieve("/categories/", anId, CategoryResponse.class);
    }

    default ResultActions updateACategory(final Identifier anId, final UpdateCategoryRequest aRequest) throws Exception {
        return this.update("/categories/", anId, aRequest);
    }

    default GenreID givenAGenre(final String aName,
                                final boolean isActive,
                                final List<CategoryID> categories) throws Exception {

        final var aRequestBody = new CreateGenreRequest(aName, isActive, mapTo(categories, CategoryID::getValue));
        final var actualId = this.given("/genres", aRequestBody);
        return GenreID.from(actualId);
    }

    default ResultActions listGenres(final int page, final int perPage, String search) throws Exception {
        return listGenres(page, perPage, search, "", "");
    }

    default ResultActions listGenres(final int page, final int perPage) throws Exception {
        return listGenres(page, perPage, "", "", "");
    }

    default ResultActions listGenres(final int page,
                                         final int perPage,
                                         final String search,
                                         final String sort,
                                         final String direction) throws Exception {
        return this.list("/genres",
                page,
                perPage,
                search,
                sort,
                direction);
    }

    default GenreResponse retrieveAGenre(final Identifier anId) throws Exception {
        return this.retrieve("/genres/", anId, GenreResponse.class);
    }

    default ResultActions updateAGenre(final Identifier anId, final UpdateGenreRequest aRequest) throws Exception {
        return this.update("/genres/", anId, aRequest);
    }

    default ResultActions deleteAGenre(final Identifier anId) throws Exception {
        return this.delete("/genres/", anId);
    }

    default <A, D> List<D> mapTo(final List<A> actual, final Function<A, D> mapper) {
        return actual.stream()
                .map(mapper)
                .toList();
    }

    private String given(final String url, final Object body) throws Exception {

        final var aRequest = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(body));

        return this.mvc().perform(aRequest)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("%s/".formatted(url), "");
    }

    private ResultActions list(
            final String url,
            final int page,
            final int perPage,
            final String search,
            final String sort,
            final String direction) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("perPage", String.valueOf(perPage))
                .queryParam("search", String.valueOf(search))
                .queryParam("sort", String.valueOf(sort))
                .queryParam("dir", String.valueOf(direction))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private <T> T retrieve(final String url, final Identifier anId, final Class<T> clazz) throws Exception {
        final var aRequest = MockMvcRequestBuilders.get(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        final var json = this.mvc().perform(aRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        return Json.readValue(json, clazz);
    }

    private ResultActions delete(final String url, final Identifier anId) throws Exception {
        final var aRequest = MockMvcRequestBuilders.delete(url + anId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return this.mvc().perform(aRequest);
    }

    private ResultActions update(final String url, final Identifier anId, final Object aBody) throws Exception {
        final var aRequest = MockMvcRequestBuilders.put(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(aBody));

        return this.mvc().perform(aRequest);
    }
}
