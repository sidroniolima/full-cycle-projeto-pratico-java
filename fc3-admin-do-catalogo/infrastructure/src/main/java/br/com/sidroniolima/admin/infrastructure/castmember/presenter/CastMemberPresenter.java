package br.com.sidroniolima.admin.infrastructure.castmember.presenter;

import br.com.sidroniolima.admin.application.castmember.retrieve.get.CastMemberOutput;
import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.infrastructure.castmember.models.CastMemberResponse;

import java.time.format.DateTimeFormatter;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput aMember) {

        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }
}
