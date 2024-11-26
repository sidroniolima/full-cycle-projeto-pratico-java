package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.events.DomainEvent;
import br.com.sidroniolima.admin.domain.utils.InstantUtils;

import java.time.Instant;

public record VideoMediaCreated(
        String resourceId,
        String filePath,
        Instant occurredOn
) implements DomainEvent {

    public VideoMediaCreated(final String resourceId, final String filePath) {
        this(resourceId, filePath, InstantUtils.now());
    }
}
