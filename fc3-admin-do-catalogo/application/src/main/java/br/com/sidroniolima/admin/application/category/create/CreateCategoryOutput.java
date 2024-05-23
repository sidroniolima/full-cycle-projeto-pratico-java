package br.com.sidroniolima.admin.application.category.create;

import br.com.sidroniolima.admin.domain.category.Category;
import br.com.sidroniolima.admin.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
