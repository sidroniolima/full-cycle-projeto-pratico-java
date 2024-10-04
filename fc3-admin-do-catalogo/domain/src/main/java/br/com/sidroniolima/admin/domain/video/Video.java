package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.AggregateRoot;
import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.domain.utils.InstantUtils;
import br.com.sidroniolima.admin.domain.validation.ValidationHandler;

import java.time.Instant;
import java.time.Year;
import java.util.Optional;
import java.util.Set;

public class Video extends AggregateRoot<VideoID> {

    private String title;
    private String description;
    private Year launchedAt;
    private double duration;
    private Rating rating;

    private boolean opened;
    private boolean published;

    private Instant createdAt;
    private Instant updatedAt;

    private ImageMedia banner;
    private ImageMedia thumbnail;
    private ImageMedia thumbnailHalf;

    private AudioVideoMedia trailer;
    private AudioVideoMedia video;

    private Set<CategoryID> categories;
    private Set<GenreID> genres;
    private Set<CastMemberID> castMembers;

    protected Video(
            final VideoID anId,
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final ImageMedia aBanner,
            final ImageMedia aThumb,
            final ImageMedia aThumbHalf,
            final AudioVideoMedia aTrailer,
            final AudioVideoMedia aVideo,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> castMembers
    ) {
        super(anId);
        this.title = aTitle;
        this.description = aDescription;
        this.launchedAt = aLaunchYear;
        this.duration = aDuration;
        this.opened = wasOpened;
        this.published = wasPublished;
        this.rating = aRating;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        this.banner = aBanner;
        this.thumbnail = aThumb;
        this.thumbnailHalf = aThumbHalf;
        this.trailer = aTrailer;
        this.video = aVideo;
        this.categories = categories;
        this.genres = genres;
        this.castMembers = castMembers;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Year getLaunchedAt() {
        return launchedAt;
    }

    public void setLaunchedAt(Year launchedAt) {
        this.launchedAt = launchedAt;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public boolean getOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Optional<ImageMedia> getBanner() {
        return Optional.ofNullable(banner);
    }

    public void setBanner(ImageMedia banner) {
        this.banner = banner;
    }

    public Optional<ImageMedia> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public void setThumbnail(ImageMedia thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Optional<ImageMedia> getThumbnailHalf() {
        return Optional.ofNullable(thumbnailHalf);
    }

    public void setThumbnailHalf(ImageMedia thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
    }

    public Optional<AudioVideoMedia> getTrailer() {
        return Optional.ofNullable(trailer);
    }

    public void setTrailer(AudioVideoMedia trailer) {
        this.trailer = trailer;
    }

    public Optional<AudioVideoMedia> getVideo() {
        return Optional.ofNullable(video);
    }

    public void setVideo(AudioVideoMedia video) {
        this.video = video;
    }

    public Set<CategoryID> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryID> categories) {
        this.categories = categories;
    }

    public Set<GenreID> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreID> genres) {
        this.genres = genres;
    }

    public Set<CastMemberID> getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(Set<CastMemberID> castMembers) {
        this.castMembers = castMembers;
    }

    public static Video newVideo(
            final String aTitle,
            final String aDescription,
            final Year aLaunchYear,
            final double aDuration,
            final boolean wasOpened,
            final boolean wasPublished,
            final Rating aRating,
            final Set<CategoryID> categories,
            final Set<GenreID> genres,
            final Set<CastMemberID> castMembers
    ) {
        final var now = InstantUtils.now();
        final var anId = VideoID.unique();

        return new Video(
                anId,
                aTitle,
                aDescription,
                aLaunchYear,
                aDuration,
                wasOpened,
                wasPublished,
                aRating,
                now,
                now,
                null,
                null,
                null,
                null,
                null,
                categories,
                genres,
                castMembers
        );
    }

    public static Video with(
            final Video aVideo
    ) {
        return new Video(
                aVideo.getId(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt(),
                aVideo.getDuration(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getBanner().orElse(null),
                aVideo.getThumbnail().orElse(null),
                aVideo.getThumbnailHalf().orElse(null),
                aVideo.getTrailer().orElse(null),
                aVideo.getVideo().orElse(null),
                aVideo.getCategories(),
                aVideo.getGenres(),
                aVideo.getCastMembers()
        );
    }
}
