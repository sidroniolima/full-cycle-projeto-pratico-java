package br.com.sidroniolima.admin.infrastructure.castmember.models;

import br.com.sidroniolima.admin.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType type) {
}
