package br.com.sidroniolima.admin.infrastructure.services.local;

import br.com.sidroniolima.admin.domain.Fixture;
import br.com.sidroniolima.admin.domain.utils.IdUtils;
import br.com.sidroniolima.admin.domain.video.VideoMediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStorageServiceTest {

    private InMemoryStorageService target = new InMemoryStorageService();

    @BeforeEach
    public void setUp() {
        this.target.reset();;
    }

    @Test
    public void givenValidResource_whenCallsStore_shouldStorageId() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        //when
        target.store(expectedName, expectedResource);

        //then
        Assertions.assertEquals(expectedResource, target.storage().get(expectedName));
    }

    @Test
    public void givenValidResource_whenCallsGet_shouldRetrieveId() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        target.storage().put(expectedName, expectedResource);

        //when
        final var actualResource = target.get(expectedName).get();

        //then
        Assertions.assertEquals(expectedResource, actualResource);
    }

    @Test
    public void givenInvalidResource_whenCallsGet_shouldBeEmpty() {
        // given
        final var expectedName = IdUtils.uuid();

        //when
        final var actualResource = target.get(expectedName);

        //then
        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    public void givenValidPrefix_whenCallsList_shouldRetrieveAll() {
        // given
        final var expectedNames = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(expectedNames);
        all.add("image_" + IdUtils.uuid());
        all.add("image_" + IdUtils.uuid());

        all.forEach(name -> target.storage().put(name, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

        //when
        final var actualResource = target.list("video");

        //then
        Assertions.assertTrue(expectedNames.size() == actualResource.size()
            && expectedNames.containsAll(actualResource)
        );
    }

    @Test
    public void givenValidNames_whenCallsDelete_shouldDeleteAll() {
        // given
        final var videos = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );

        final var expectedNames = Set.of(
                "image_" + IdUtils.uuid(),
                "image_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(videos);
        all.addAll(expectedNames);

        all.forEach(name -> target.storage().put(name, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

        //when
        target.deleteAll(videos);

        //then
        Assertions.assertEquals(2, target.storage().size());
        final var actualKeys = target.storage().keySet();

        Assertions.assertTrue(expectedNames.size() == actualKeys.size()
                && expectedNames.containsAll(actualKeys)
        );
    }
}