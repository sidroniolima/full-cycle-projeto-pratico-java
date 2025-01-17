package br.com.sidroniolima.admin.infrastructure.amqp;

import br.com.sidroniolima.admin.application.video.media.update.UpdateMediaStatusCommand;
import br.com.sidroniolima.admin.application.video.media.update.UpdateMediaStatusUseCase;
import br.com.sidroniolima.admin.domain.video.MediaStatus;
import br.com.sidroniolima.admin.infrastructure.configuration.json.Json;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoEncoderCompleted;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoEncoderError;
import br.com.sidroniolima.admin.infrastructure.video.models.VideoEncoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class VideoEncoderListener {

    private static final Logger log = LoggerFactory.getLogger(VideoEncoderListener.class);

    static final String LISTENER_ID = "videoEncodedListener";

    private final UpdateMediaStatusUseCase updateMediaStatusUseCase;

    public VideoEncoderListener(final UpdateMediaStatusUseCase updateMediaStatusUseCase) {
        this.updateMediaStatusUseCase = updateMediaStatusUseCase;
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.video-encoded.queue}")
    public void onVideoEncodedMessage(@Payload final String message) {
        final var aResult = Json.readValue(message, VideoEncoderResult.class);

        if (aResult instanceof VideoEncoderCompleted dto) {
            log.error("[message:video_listener.income] [status:completed] [payload:{}]", message);

            final var aCmd = new UpdateMediaStatusCommand(
                    MediaStatus.COMPLETED,
                    dto.id(),
                    dto.video().resourceId(),
                    dto.video().encodedVideoFolder(),
                    dto.video().filePath()
            );

            this.updateMediaStatusUseCase.execute(aCmd);

        } else if (aResult instanceof VideoEncoderError dto) {
            log.error("[message:video_listener.income] [status:error] [payload:{}]", message);
        } else {
            log.error("[message:video_listener.income] [status:unknown] [payload:{}]", message);
        }
    }
}
