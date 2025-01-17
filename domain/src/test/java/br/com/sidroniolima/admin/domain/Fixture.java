package br.com.sidroniolima.admin.domain;

import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberType;
import br.com.sidroniolima.admin.domain.category.Category;
import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.utils.IdUtils;
import br.com.sidroniolima.admin.domain.video.*;
import br.com.sidroniolima.admin.domain.resource.Resource;
import com.github.javafaker.Faker;

import java.time.Year;
import java.util.Set;

import static io.vavr.API.*;

public final class Fixture {
    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static Integer year() {
        return FAKER.random().nextInt(2020, 2030);
    }

    public static double duration() {
        return FAKER.options().option(12.0, 15.5, 35.5, 10.0, 2.0);
    }

    public static boolean bool() {
        return FAKER.bool().bool();
    }

    public static String title() {
        return FAKER.options().option(
                "Title 1",
                "Title 2",
                "Title 3"
        );
    }

    public static Video video() {
        return Video.newVideo(
                Fixture.title(),
                Videos.description(),
                Year.of(Fixture.year()),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                Videos.rating(),
                Set.of(Categories.aulas().getId()),
                Set.of(Genres.tech().getId()),
                Set.of(CastMembers.wesley().getId())
        );
    }

    public static final class CastMembers {

        private static final CastMember WESLEY =
                CastMember.newMember("Wesley Fullcycle", CastMemberType.ACTOR);

        private static final CastMember SIDRONIO =
                CastMember.newMember("Sidronio Lima", CastMemberType.ACTOR);

        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.values());
        }

        public static CastMember wesley() {
            return CastMember.with(WESLEY);
        }

        public static CastMember sidronio() {
            return CastMember.with(SIDRONIO);
        }
    }

    public static final class Categories {
        private static final Category AULAS =
                Category.newCategory("Aulas", "Some description", true);

        public static Category aulas() {
            return AULAS.clone();
        }
    }

    public static final class Videos {

        public static Video systemDesign() {
            return Video.newVideo(
                    Fixture.title(),
                    description(),
                    Year.of(Fixture.year()),
                    Fixture.duration(),
                    Fixture.bool(),
                    Fixture.bool(),
                    rating(),
                    Set.of(Categories.aulas().getId()),
                    Set.of(Genres.tech().getId()),
                    Set.of(CastMembers.wesley().getId())
            );
        }

        public static Resource resource(final VideoMediaType type) {
            final String contentType = Match(type).of(
                    Case($(List(VideoMediaType.VIDEO,VideoMediaType.TRAILER)::contains), "video/mp4"),
                    Case($(), "image/jpg")
            );

            final String checksum = IdUtils.uuid();
            final byte[] content = "Content".getBytes();

            return Resource.with(checksum, content, contentType, type.name().toLowerCase());
        }

        public static AudioVideoMedia audioVideo(final VideoMediaType type) {
            final var id = IdUtils.uuid();
            final var name = type.name().toLowerCase() + "_" + id;
            return AudioVideoMedia.with(
                    id,
                    name,
                    "/raw/".concat(name)
            );
        }

        public static ImageMedia imageMedia(final VideoMediaType type) {
            final var id = IdUtils.uuid();
            final var name = type.name().toLowerCase() + "_" + id;
            return ImageMedia.with(
                    id,
                    name,
                    "/raw/".concat(name)
            );
        }

        public static String description() {
            return FAKER.options().option(
                    "Description 1",
                    "Description 2",
                    "Description 3"
            );
        }

        public static Rating rating() {
            return FAKER.options().option(Rating.values());
        }

        public static VideoMediaType mediaType() {
            return FAKER.options().option(VideoMediaType.values());
        }
    }

    public static final class Genres {
        private static final Genre TECH = Genre.newGenre("Technology", true);

        public static Genre tech() {
            return Genre.with(TECH);
        }
    }
}
