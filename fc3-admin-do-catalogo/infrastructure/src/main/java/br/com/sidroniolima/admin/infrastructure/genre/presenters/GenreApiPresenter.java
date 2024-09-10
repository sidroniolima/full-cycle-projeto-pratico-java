package br.com.sidroniolima.admin.infrastructure.genre.presenters;

import br.com.sidroniolima.admin.application.genre.retrieve.get.GenreOutput;
import br.com.sidroniolima.admin.application.genre.retrieve.list.GenreListOutput;
import br.com.sidroniolima.admin.infrastructure.genre.models.GenreListResponse;
import br.com.sidroniolima.admin.infrastructure.genre.models.GenreResponse;

import java.util.function.Function;

public interface GenreApiPresenter {

    Function<GenreOutput, GenreResponse> present =
            output -> new GenreResponse(
                    output.id(),
                    output.name(),
                    output.isActive(),
                    output.categories(),
                    output.createdAt(),
                    output.updatedAt(),
                    output.deletedAt()
            );

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.categories(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
