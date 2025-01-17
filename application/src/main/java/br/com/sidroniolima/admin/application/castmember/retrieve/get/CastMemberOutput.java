package br.com.sidroniolima.admin.application.castmember.retrieve.get;

import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberOutput(String id,
                               String name,
                               CastMemberType type,
                               Instant createdAt,
                               Instant updatedAt
) {

    public static CastMemberOutput from(CastMember aMember) {
        return new CastMemberOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt(),
                aMember.getUpdatedAt()
        );
    }
}
