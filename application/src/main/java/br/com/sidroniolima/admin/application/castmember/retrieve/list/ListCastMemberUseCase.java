package br.com.sidroniolima.admin.application.castmember.retrieve.list;

import br.com.sidroniolima.admin.application.UseCase;
import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;

public sealed abstract class ListCastMemberUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMemberUseCase
{
}
