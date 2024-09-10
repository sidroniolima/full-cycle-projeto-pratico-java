package br.com.sidroniolima.admin.infrastructure.configuration.usecases;

import br.com.sidroniolima.admin.application.genre.create.CreateGenreUseCase;
import br.com.sidroniolima.admin.application.genre.create.DefaultCreateGenreUseCase;
import br.com.sidroniolima.admin.application.genre.delete.DefaultGenreDeleteUseCase;
import br.com.sidroniolima.admin.application.genre.delete.DeleteGenreUseCase;
import br.com.sidroniolima.admin.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import br.com.sidroniolima.admin.application.genre.retrieve.get.GetGenreByIdUseCase;
import br.com.sidroniolima.admin.application.genre.retrieve.list.DefaultListGenreUseCase;
import br.com.sidroniolima.admin.application.genre.retrieve.list.ListGenreUseCase;
import br.com.sidroniolima.admin.application.genre.update.DefaultUpdateGenreUseCase;
import br.com.sidroniolima.admin.application.genre.update.UpdateGenreUseCase;
import br.com.sidroniolima.admin.domain.category.CategoryGateway;
import br.com.sidroniolima.admin.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {
    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public GenreUseCaseConfig(final CategoryGateway categoryGateway,
                              final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultGenreDeleteUseCase(genreGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(genreGateway, categoryGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenreUseCase listGenreUseCase() {
        return new DefaultListGenreUseCase(genreGateway);
    }
}
