package br.com.sidroniolima.admin.application.castmember.update;

import br.com.sidroniolima.admin.domain.Identifier;
import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberGateway;
import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.domain.exceptions.NotFoundException;
import br.com.sidroniolima.admin.domain.exceptions.NotificationException;
import br.com.sidroniolima.admin.domain.validation.handler.Notification;

import java.util.Objects;
import java.util.function.Supplier;

public non-sealed class DefaultUpdateCastMemberUseCase extends UpdateCastMemberUseCase{

    private final CastMemberGateway castMemberGateway;

    public DefaultUpdateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public UpdateCastMemberOutput execute(UpdateCastMemberCommand aCommand) {
        final var anId = CastMemberID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aType = aCommand.type();

        final var aMember = this.castMemberGateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.validate(() -> aMember.update(aName, aType));

        if (notification.hasError()) {
            notify(anId, notification);
        }

        return UpdateCastMemberOutput.from(this.castMemberGateway.update(aMember));
    }

    private void notify(final Identifier anId, final Notification notification) {
        throw new NotificationException("Could not update Aggregated CastMember %s".formatted(anId.getValue()), notification);
    }

    private Supplier<NotFoundException> notFound(final CastMemberID anId) {
        return () -> NotFoundException.with(CastMember.class, anId);
    }
}
