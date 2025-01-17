package br.com.sidroniolima.admin.application.genre.delete;

import br.com.sidroniolima.admin.domain.genre.GenreGateway;
import br.com.sidroniolima.admin.domain.genre.GenreID;

import java.util.Objects;

public class DefaultGenreDeleteUseCase extends DeleteGenreUseCase {

    private final GenreGateway genreGateway;

    public DefaultGenreDeleteUseCase(GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public void execute(final String anId) {
        this.genreGateway.deleteById(GenreID.from(anId));
    }
}
