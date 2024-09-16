package br.com.sidroniolima.admin.application.castmember.retrieve.get;

import br.com.sidroniolima.admin.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {
}
