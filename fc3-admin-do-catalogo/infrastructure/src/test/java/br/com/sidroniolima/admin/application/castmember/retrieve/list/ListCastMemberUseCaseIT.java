package br.com.sidroniolima.admin.application.castmember.retrieve.list;

import br.com.sidroniolima.admin.Fixture;
import br.com.sidroniolima.admin.IntegrationTest;
import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberGateway;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;
import br.com.sidroniolima.admin.infrastructure.castmember.persistence.CastMemberJpaEntity;
import br.com.sidroniolima.admin.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class ListCastMemberUseCaseIT {

    @Autowired
    private ListCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    public void givenAValidQuery_whenCallsListCastMembers_shouldReturnAll() {
        // given
        final var members = List.of(
                CastMember.newMember(Fixture.name(), Fixture.CastMember.type()),
                CastMember.newMember(Fixture.name(), Fixture.CastMember.type())
        );

        this.castMemberRepository.saveAllAndFlush(
                members
                        .stream()
                        .map(CastMemberJpaEntity::from)
                        .toList()
        );

        Assertions.assertEquals(2, this.castMemberRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;

        final var expectedItems = members.stream()
                .map(CastMemberListOutput::from)
                .toList();

        final var aQuery = new SearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualOutput = useCase.execute(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertTrue(expectedItems.size() == actualOutput.items().size()
            && expectedItems.containsAll(actualOutput.items())
        );

        verify(castMemberGateway).findAll(any());
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMembersAndIsEmpty_shouldReturn() {
        // given
        final var members = List.<CastMember>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Algo";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedItems = List.<CastMemberListOutput>of();

        Assertions.assertEquals(0, this.castMemberRepository.count());

        final var aQuery = new SearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualOutput = useCase.execute(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        verify(castMemberGateway).findAll(any());
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMembersAndGatewayThrowsRandomException_shouldReturnException() {
        // given
        final var members = List.<CastMember>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Algo";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedErrorMessage = "Gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(castMemberGateway).findAll(any());

        final var aQuery = new SearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualException = Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute(aQuery));

        // then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(castMemberGateway).findAll(any());
    }
}
