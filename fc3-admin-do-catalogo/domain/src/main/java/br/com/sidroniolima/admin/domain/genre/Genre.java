package br.com.sidroniolima.admin.domain.genre;

import br.com.sidroniolima.admin.domain.AggregateRoot;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.exceptions.NotificationException;
import br.com.sidroniolima.admin.domain.utils.InstantUtils;
import br.com.sidroniolima.admin.domain.validation.ValidationHandler;
import br.com.sidroniolima.admin.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genre extends AggregateRoot<GenreID> {

    private String name;
    private boolean active;
    private List<CategoryID> categories;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    protected Genre(GenreID anId,
                    final String aName,
                    final boolean isActive,
                    final List<CategoryID> categories,
                    final Instant createdAt,
                    final Instant updatedAt,
                    final Instant deletedAt) {
        super(anId);

        this.name = aName;
        this.active = isActive;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;

        selfValidate();
    }

    public static Genre newGenre(final String aName, final boolean isActive) {
        final var anId = GenreID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new Genre(anId, aName, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre with(GenreID anId,
                    final String aName,
                    final boolean isActive,
                    final List<CategoryID> categories,
                    final Instant createdAt,
                    final Instant updatedAt,
                    final Instant deletedAt) {

        return new Genre(anId, aName, isActive, categories, createdAt, updatedAt, deletedAt);
    }

    public static Genre with(Genre aGenre) {
        return new Genre(
                aGenre.id,
                aGenre.name,
                aGenre.active,
                new ArrayList<>(aGenre.categories),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public Genre update(final String aName, final boolean isActive, final List<CategoryID> categories) {
        this.name = aName;

        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.active = isActive;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();

        selfValidate();

        return this;
    }

    public Genre deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();

        return this;
    }

    public Genre addCategory(final CategoryID aCategoryID) {
        if (null == aCategoryID) {
            return this;
        }

        this.categories.add(aCategoryID);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public Genre addCategories(final List<CategoryID> categories) {
        if (null == categories || categories.isEmpty()) {
            return this;
        }

        this.categories.addAll(categories);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public Genre removeCategory(CategoryID aCategoryID) {
        if (null == aCategoryID) {
            return this;
        }

        this.categories.remove(aCategoryID);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public String getName() {
        return name;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create an Aggregate Genre", notification);
        }
    }
}
