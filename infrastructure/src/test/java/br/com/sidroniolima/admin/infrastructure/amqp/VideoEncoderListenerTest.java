package br.com.sidroniolima.admin.infrastructure.amqp;

import br.com.sidroniolima.admin.AmqpTest;
import br.com.sidroniolima.admin.application.video.media.update.UpdateMediaStatusCommand;
import br.com.sidroniolima.admin.application.video.media.update.UpdateMediaStatusUseCase;
import br.com.sidroniolima.admin.domain.utils.IdUtils;
import br.com.sidroniolima.admin.domain.video.MediaStatus;
import br.com.sidroniolima.admin.infrastructure.configuration.annotations.VideoEncodedQueue;
import br.com.sidroniolima.admin.infrastructure.configuration.json.Json;
import br.com.sidroniolima.admin.infrastructure.configuration.properties.amqp.QueueProperties;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoEncoderCompleted;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoEncoderError;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoMessage;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;

@AmqpTest
class VideoEncoderListenerTest {

    @Autowired
    private TestRabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @MockBean
    private UpdateMediaStatusUseCase updateMediaStatusUseCase;

    @Autowired
    @VideoEncodedQueue
    private QueueProperties queueProperties;

    @Test
    public void givenErrorResult_whenCallsListener_shouldProcess() throws InterruptedException {
        // given
        final var expectedError = new VideoEncoderError(
                new VideoMessage("123", "abc"),
                "Video not found"
        );

        final var expectedMessage = Json.writeValueAsString(expectedError);

        // when

        this.rabbitTemplate.convertAndSend(queueProperties.getQueue(), expectedMessage);

        // then
        final var invocationData =
                harness.getNextInvocationDataFor(VideoEncoderListener.LISTENER_ID, 1, TimeUnit.SECONDS);

        Assertions.assertNotNull(invocationData);
        Assertions.assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenCompletedResult_whenCallsListener_shouldCAllUseCase() throws InterruptedException {
        // given
        final var expectedId = IdUtils.uuid();
        final var expectedOutputBucket = "codeeducationtest";
        final var expectedStatus = MediaStatus.COMPLETED;
        final var expectedEncoderVideoFolder = "anyFolder";
        final var expectedResourceId = IdUtils.uuid();
        final var expectedFilePath = "any.mp4";
        final var expectedMetadata = new VideoMetadata(expectedEncoderVideoFolder, expectedResourceId, expectedFilePath);

        final var aReulst = new VideoEncoderCompleted(expectedId,
                expectedOutputBucket,
                expectedMetadata);

        final var expectedMessage = Json.writeValueAsString(aReulst);

        Mockito.doNothing().when(updateMediaStatusUseCase).execute(Mockito.any());

        // when
        this.rabbitTemplate.convertAndSend(queueProperties.getQueue(), expectedMessage);

        // then
        final var invocationData =
                harness.getNextInvocationDataFor(VideoEncoderListener.LISTENER_ID, 1, TimeUnit.SECONDS);

        Assertions.assertNotNull(invocationData);
        Assertions.assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];
        Assertions.assertEquals(expectedMessage, actualMessage);

        final var cmdCaptor = ArgumentCaptor.forClass(UpdateMediaStatusCommand.class);
        Mockito.verify(updateMediaStatusUseCase).execute(cmdCaptor.capture());

        final var actualCommand = cmdCaptor.getValue();
        Assertions.assertEquals(expectedStatus, actualCommand.status());
        Assertions.assertEquals(expectedId, actualCommand.videoId());
        Assertions.assertEquals(expectedResourceId, actualCommand.resourceId());
        Assertions.assertEquals(expectedEncoderVideoFolder, actualCommand.folder());
        Assertions.assertEquals(expectedFilePath, actualCommand.fileName());
    }
}