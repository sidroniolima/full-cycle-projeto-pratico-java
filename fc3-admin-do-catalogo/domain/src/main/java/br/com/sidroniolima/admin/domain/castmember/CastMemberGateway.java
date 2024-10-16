package br.com.sidroniolima.admin.domain.castmember;

import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface CastMemberGateway {
    CastMember create(CastMember aGenre);
    void deleteById(CastMemberID anId);
    Optional<CastMember> findById(CastMemberID anId);
    CastMember update(CastMember aGenre);
    Pagination<CastMember> findAll(SearchQuery aQuery);
    List<CastMemberID> existsByIds(Iterable<CastMemberID> ids);
}
