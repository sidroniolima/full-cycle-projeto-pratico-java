package br.com.sidroniolima.admin.domain.castmember;

import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {
    Genre create(CastMember aGenre);
    void deleteById(CastMemberID anId);
    Optional<CastMember> findById(CastMemberID anId);
    CastMember update(CastMember aGenre);
    Pagination<CastMember> findAll(SearchQuery aQuery);
}
