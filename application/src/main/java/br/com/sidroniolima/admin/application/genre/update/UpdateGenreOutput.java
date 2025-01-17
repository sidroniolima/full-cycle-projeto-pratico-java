package br.com.sidroniolima.admin.application.genre.update;

import br.com.sidroniolima.admin.domain.genre.Genre;

public record UpdateGenreOutput(String id) {
    public static UpdateGenreOutput from(final Genre aGenre) {
        return new UpdateGenreOutput(aGenre.getId().getValue());
    }
}
