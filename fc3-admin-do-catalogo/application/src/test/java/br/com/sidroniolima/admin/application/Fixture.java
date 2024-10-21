package br.com.sidroniolima.admin.application;

import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberType;
import br.com.sidroniolima.admin.domain.category.Category;
import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.video.Rating;
import br.com.sidroniolima.admin.domain.video.Resource;
import com.github.javafaker.Faker;

import java.util.Arrays;

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

        public static Resource resource(final Resource.Type type) {
            final String contentType = Match(type).of(
                    Case($(List(Resource.Type.VIDEO, Resource.Type.TRAILER)::contains), "video/mp4"),
                    Case($(), "image/jpg")
            );

            final byte[] content = "Content".getBytes();

            return Resource.with(content, contentType, type.name().toLowerCase(), type);
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
    }

    public static final class Genres {
        private static final Genre TECH = Genre.newGenre("Technology", true);

        public static Genre tech() {
            return Genre.with(TECH);
        }
    }
}
