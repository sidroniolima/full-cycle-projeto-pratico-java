package br.com.sidroniolima.admin.infrastructure.video;

import br.com.sidroniolima.admin.domain.Identifier;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.video.*;
import br.com.sidroniolima.admin.infrastructure.configuration.annotations.VideoCreatedQueue;
import br.com.sidroniolima.admin.infrastructure.services.EventService;
import br.com.sidroniolima.admin.infrastructure.utils.SqlUtils;
import br.com.sidroniolima.admin.infrastructure.video.persistence.VideoJpaEntity;
import br.com.sidroniolima.admin.infrastructure.video.persistence.VideoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static br.com.sidroniolima.admin.domain.utils.CollectionUtils.mapTo;
import static br.com.sidroniolima.admin.domain.utils.CollectionUtils.nullIfEmpty;

@Component
public class DefaultVideoGateway implements VideoGateway {

    private final EventService eventsService;
    private final VideoRepository videoRepository;

    public DefaultVideoGateway(
            @VideoCreatedQueue final EventService eventsService,
            final VideoRepository videoRepository) {
        this.eventsService =  Objects.requireNonNull(eventsService);
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }

    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return this.save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var aVideoId = anId.getValue();
        if (this.videoRepository.existsById(aVideoId)) {
            this.videoRepository.deleteById(aVideoId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findById(VideoID anId) {
        return this.videoRepository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    @Transactional
    public Video update(Video aVideo) {
        return this.save(aVideo);
    }

    @Override
    public Pagination<VideoPreview> findAll(VideoSearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var actualPage = this.videoRepository.findAll(
                SqlUtils.like(SqlUtils.upper(aQuery.terms())),
                nullIfEmpty(mapTo(aQuery.castMembers(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.categories(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.genres(), Identifier::getValue)),
                page
        );

        return new Pagination<VideoPreview>(
                actualPage.getNumber(),
                actualPage.getSize(),
                actualPage.getTotalElements(),
                actualPage.toList()
        );
    }

    private Video save(final Video aVideo) {
        final var result = this.videoRepository.save(VideoJpaEntity.from(aVideo))
                .toAggregate();

        aVideo.publishDomainEvents(this.eventsService::send);

        return result;
    }
}
