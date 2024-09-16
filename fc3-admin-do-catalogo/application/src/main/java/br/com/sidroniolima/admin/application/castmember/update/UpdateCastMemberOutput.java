package br.com.sidroniolima.admin.application.castmember.update;

import br.com.sidroniolima.admin.domain.castmember.CastMember;

public record UpdateCastMemberOutput(
        String id
) {
    public static UpdateCastMemberOutput from(CastMember aMember) {
        return new UpdateCastMemberOutput(aMember.getId().getValue());
    }
}
