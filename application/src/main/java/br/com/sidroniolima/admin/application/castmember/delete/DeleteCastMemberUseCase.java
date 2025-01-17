package br.com.sidroniolima.admin.application.castmember.delete;

import br.com.sidroniolima.admin.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase
        extends UnitUseCase<String>
        permits DefaultDeleteCastMemberUseCase
{
}
