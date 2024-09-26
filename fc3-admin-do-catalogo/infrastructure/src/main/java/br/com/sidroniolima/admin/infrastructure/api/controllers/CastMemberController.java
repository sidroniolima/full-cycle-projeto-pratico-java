package br.com.sidroniolima.admin.infrastructure.api.controllers;

import br.com.sidroniolima.admin.application.castmember.create.CreateCastMemberCommand;
import br.com.sidroniolima.admin.application.castmember.create.CreateCastMemberUseCase;
import br.com.sidroniolima.admin.application.castmember.delete.DeleteCastMemberUseCase;
import br.com.sidroniolima.admin.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.sidroniolima.admin.application.castmember.update.UpdateCastMemberCommand;
import br.com.sidroniolima.admin.application.castmember.update.UpdateCastMemberUseCase;
import br.com.sidroniolima.admin.infrastructure.api.CastMemberAPI;
import br.com.sidroniolima.admin.infrastructure.castmember.models.CastMemberResponse;
import br.com.sidroniolima.admin.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.sidroniolima.admin.infrastructure.castmember.models.UpdateCastMemberRequest;
import br.com.sidroniolima.admin.infrastructure.castmember.presenter.CastMemberPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;
    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;
    private final UpdateCastMemberUseCase updateCastMemberUseCase;
    private final DeleteCastMemberUseCase deleteCastMemberUseCase;

    public CastMemberController(final CreateCastMemberUseCase createCastMemberUseCase,
                                final GetCastMemberByIdUseCase getCastMemberByIdUseCase,
                                final UpdateCastMemberUseCase updateCastMemberUseCase,
                                final DeleteCastMemberUseCase deleteCastMemberUseCase) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.getCastMemberByIdUseCase = Objects.requireNonNull(getCastMemberByIdUseCase);
        this.updateCastMemberUseCase = Objects.requireNonNull(updateCastMemberUseCase);
        this.deleteCastMemberUseCase = Objects.requireNonNull(deleteCastMemberUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateCastMemberRequest input) {
        final var aCommand = CreateCastMemberCommand.with(input.name(), input.type());

        final var output = this.createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

    @Override
    public CastMemberResponse getById(final String id) {
        return CastMemberPresenter.present(this.getCastMemberByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCastMemberRequest aBody) {
        final var aCommand =
                UpdateCastMemberCommand.with(id, aBody.name(), aBody.type());

        final var output = this.updateCastMemberUseCase.execute(aCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteCastMemberUseCase.execute(anId);
    }
}
