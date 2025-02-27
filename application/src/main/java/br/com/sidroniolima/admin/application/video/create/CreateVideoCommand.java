package br.com.sidroniolima.admin.application.video.create;

import br.com.sidroniolima.admin.domain.resource.Resource;

import java.util.Optional;
import java.util.Set;

public record CreateVideoCommand(
        String title,
        String description,
        Integer launchedAt,
        Double duration,
        Boolean opened,
        Boolean published,
        String rating,
        Set<String> members,
        Set<String> categories,
        Set<String> genres,
        Resource video,
        Resource trailer,
        Resource banner,
        Resource thumbnail,
        Resource thumbnailHalf
) {

        public static CreateVideoCommand with(
                final String title,
                final String description,
                final Integer launchedAt,
                final Double duration,
                final Boolean opened,
                final Boolean published,
                final String rating,
                final Set<String> members,
                final Set<String> categories,
                final Set<String> genres,
                final Resource video,
                final Resource banner,
                final Resource trailer,
                final Resource thumbnail,
                final Resource thumbnailHalf
        ) {
                return new CreateVideoCommand(
                        title,
                        description,
                        launchedAt,
                        duration,
                        opened,
                        published,
                        rating,
                        members,
                        categories,
                        genres,
                        video,
                        banner,
                        trailer,
                        thumbnail,
                        thumbnailHalf
                );
        }

        public static CreateVideoCommand with(
                final String title,
                final String description,
                final Integer launchedAt,
                final Double duration,
                final Boolean opened,
                final Boolean published,
                final String rating,
                final Set<String> castMembers,
                final Set<String> categories,
                final Set<String> genres
        ) {
                return new CreateVideoCommand(
                        title,
                        description,
                        launchedAt,
                        duration,
                        opened,
                        published,
                        rating,
                        castMembers,
                        categories,
                        genres,
                        null,
                        null,
                        null,
                        null,
                        null
                );
        }

        public Optional<Resource> getVideo() {
                return Optional.ofNullable(video);
        }

        public Optional<Resource> getTrailer() {
                return Optional.ofNullable(trailer);
        }

        public Optional<Resource> getBanner() {
                return Optional.ofNullable(banner);
        }

        public Optional<Resource> getThumbnail() {
                return Optional.ofNullable(thumbnail);
        }

        public Optional<Resource> getThumbnailHalf() {
                return Optional.ofNullable(thumbnailHalf);
        }
}
