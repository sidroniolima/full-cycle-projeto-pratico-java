package br.com.sidroniolima.admin.application.castmember.create;

import br.com.sidroniolima.admin.application.UseCase;

// sealed: só pode ser estendida por quem a classe pertime
public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {
}
