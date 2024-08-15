package br.com.sidroniolima.admin.application.category.retrieve.get;

import br.com.sidroniolima.admin.domain.category.Category;
import br.com.sidroniolima.admin.domain.category.CategoryID;

import java.time.Instant;

public record GetCategoryByIdOutput(CategoryID id,
                                    String name,
                                    String description,
                                    boolean isActive,
                                    Instant createdAt,
                                    Instant updatedAt,
                                    Instant deletedAt) {

    public static GetCategoryByIdOutput from(final Category aCategory) {
        return new GetCategoryByIdOutput(aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt());
    }
}
