package br.com.sidroniolima.admin.application.castmember.update;

import br.com.sidroniolima.admin.domain.castmember.CastMemberType;

public record UpdateCastMemberCommand(
        String id,
        String name,
        CastMemberType type
) {

    public static UpdateCastMemberCommand with(
            String anId,
            String aName,
            CastMemberType aType
    ) {
        return new UpdateCastMemberCommand(anId, aName, aType);
    }
}
