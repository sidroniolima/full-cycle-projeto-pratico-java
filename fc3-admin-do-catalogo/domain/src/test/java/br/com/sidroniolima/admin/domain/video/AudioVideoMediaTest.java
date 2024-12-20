package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.UnitTest;
import br.com.sidroniolima.admin.domain.utils.IdUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoMediaTest extends UnitTest {

    @Test
    public void givenValidParams_whenCallsNewAudioVideo_shouldReturnInstance() {
        // given
        final var expectedId = IdUtils.uuid();
        final var expectedChecksum = "abc";
        final var expectedName = "Banner.png";
        final var expectedRawLocation = "/image/ac";
        final var expectedEncodedLocation = "/image/ac";
        final var expectedStatus = MediaStatus.PENDING;

        // when
        final var actualVideo =
                AudioVideoMedia.with(expectedId, expectedChecksum, expectedName, expectedRawLocation, expectedEncodedLocation, expectedStatus);

        // then
        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedId, actualVideo.id());
        Assertions.assertEquals(expectedChecksum, actualVideo.checksum());
        Assertions.assertEquals(expectedName, actualVideo.name());
        Assertions.assertEquals(expectedRawLocation, actualVideo.rawLocation());
        Assertions.assertEquals(expectedEncodedLocation, actualVideo.encodedLocation());
        Assertions.assertEquals(expectedStatus, actualVideo.status());
    }

    @Test
    public void givenTwoAudioVideoMediaWithSameChecksumAndLocation_whenCallsEquals_shouldReturnTrue() {
        // given
        final var expectedChecksum = "abc";
        final var expectedLocation = "/image/ac";

        // when
        final var video1 =
                AudioVideoMedia.with(expectedChecksum, "Random", expectedLocation);

        final var video2 = AudioVideoMedia.with(expectedChecksum, "Random", expectedLocation);

        // then
        Assertions.assertEquals(video1, video2);
        Assertions.assertNotSame(video1, video2);
    }

    @Test
    public void givenInvalidParams_whenCallsWith_shouldReturnError() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with(null, "123", "Random", "/videos", "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id", "abc", null, "/videos", "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id","abc", "Random", null, "/videos", MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id","abc", "Random", "/videos", null, MediaStatus.PENDING)
        );

        Assertions.assertThrows(
                NullPointerException.class,
                () -> AudioVideoMedia.with("id","abc", "Random", "/videos", "/videos", null)
        );

    }
}