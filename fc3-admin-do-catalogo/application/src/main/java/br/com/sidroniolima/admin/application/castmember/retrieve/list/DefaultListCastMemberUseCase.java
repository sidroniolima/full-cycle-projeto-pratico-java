package br.com.sidroniolima.admin.application.castmember.retrieve.list;

import br.com.sidroniolima.admin.domain.castmember.CastMemberGateway;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

import java.util.Objects;

public final class DefaultListCastMemberUseCase extends ListCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultListCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(SearchQuery anIn) {
        return this.castMemberGateway.findAll(anIn)
                .map(CastMemberListOutput::from);
    }
}
