package br.com.sidroniolima.admin.application.video.create;

import br.com.sidroniolima.admin.domain.video.Resource;

import java.util.Set;

public record CreateVideoCommand(
        String title,
        String description,
        int launchedAt,
        double duration,
        boolean opened,
        boolean published,
        String rating,
        Set<String> categories,
        Set<String> genres,
        Set<String> members,
        Resource video,
        Resource trailer,
        Resource thumbnail,
        Resource thumbnailHalf
) {

        public static CreateVideoCommand with(
                String title,
                String description,
                int launchedAt,
                double duration,
                boolean opened,
                boolean published,
                String rating,
                Set<String> categories,
                Set<String> genres,
                Set<String> members,
                Resource video,
                Resource trailer,
                Resource thumbnail,
                Resource thumbnailHalf
        ) {
                return new CreateVideoCommand(
                        title, description,
                        launchedAt,
                        duration,
                        opened,
                        published,
                        rating,
                        categories,
                        genres,
                        members,
                        video,
                        trailer,
                        thumbnail,
                        thumbnailHalf
                );
        }
}
