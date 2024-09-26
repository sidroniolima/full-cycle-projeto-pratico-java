package br.com.sidroniolima.admin.application.castmember.create;

import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberGateway;
import br.com.sidroniolima.admin.domain.exceptions.NotificationException;
import br.com.sidroniolima.admin.domain.validation.handler.Notification;

import java.util.Objects;
// non-sealed pois caso contrário o Mock não consegue estendê-la
public non-sealed class DefaultCreateCastMemberUseCase extends CreateCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultCreateCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    //Fake destructoring = cópia defensiva
    @Override
    public CreateCastMemberOutput execute(final CreateCastMemberCommand anCommand) {
        final var aName = anCommand.name();
        final var aType = anCommand.type();

        final var notification = Notification.create();

        final var aMember = notification.validate(() -> CastMember.newMember(aName, aType));

        if (notification.hasError()) {
            notify(notification);
        }

        return CreateCastMemberOutput.from(this.castMemberGateway.create(aMember));
    }

    private void notify(Notification notification) {
        throw new NotificationException("Could not create Aggregate CastMember", notification);
    }
}
