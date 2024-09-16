package br.com.sidroniolima.admin.application.castmember.update;

import br.com.sidroniolima.admin.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
    permits DefaultUpdateCastMemberUseCase
{
}
