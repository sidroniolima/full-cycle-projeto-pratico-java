package br.com.sidroniolima.admin.infrastructure.castmember;

import br.com.sidroniolima.admin.MySQLGatewayTest;
import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.domain.castmember.CastMemberType;
import br.com.sidroniolima.admin.domain.genre.Genre;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.domain.pagination.SearchQuery;
import br.com.sidroniolima.admin.infrastructure.castmember.persistence.CastMemberJpaEntity;
import br.com.sidroniolima.admin.infrastructure.castmember.persistence.CastMemberRepository;
import br.com.sidroniolima.admin.infrastructure.genre.persistence.GenreJpaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static br.com.sidroniolima.admin.domain.Fixture.CastMembers.type;
import static br.com.sidroniolima.admin.domain.Fixture.name;

@MySQLGatewayTest
public class CastMembersMySQLGatewayTest {

    @Autowired
    private CastMemberMySQLGateway castMemberGateway;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void testDependencies() {
        Assertions.assertNotNull(castMemberGateway);
        Assertions.assertNotNull(castMemberRepository);
    }

    @Test
    public void givenAValidCastMember_whenCallsCreate_shouldPersistIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        Assertions.assertEquals(0, castMemberRepository.count());

        //when
        final var actualMember = castMemberGateway.create(CastMember.with(aMember));

        //then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());

        final var persistedMember = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedMember.getId());
        Assertions.assertEquals(expectedName, persistedMember.getName());
        Assertions.assertEquals(expectedType, persistedMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), persistedMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), persistedMember.getUpdatedAt());
    }

    @Test
    public void givenAValidCastMember_whenCallsUpdate_shouldRefreshIt() {
        // given
        final var expectedName = name();
        final var expectedType = CastMemberType.ACTOR;

        final var aMember = CastMember.newMember("vindi", CastMemberType.DIRECTOR);
        final var expectedId = aMember.getId();

        final var currentMember = castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());
        Assertions.assertEquals("vindi", currentMember.getName());
        Assertions.assertEquals(CastMemberType.DIRECTOR, currentMember.getType());

        //when
        final var actualMember = castMemberGateway.update(
                CastMember.with(aMember).update(expectedName, expectedType)
        );

        //then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(actualMember.getUpdatedAt()));

        final var persistedMember = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedMember.getId());
        Assertions.assertEquals(expectedName, persistedMember.getName());
        Assertions.assertEquals(expectedType, persistedMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), persistedMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(persistedMember.getUpdatedAt()));
    }

    @Test
    public void givenAValidCastMember_whenCallsDeleteById_shouldDeleteIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        //when
        castMemberGateway.deleteById(expectedId);

        //then
        Assertions.assertEquals(0, castMemberRepository.count());
    }

    @Test
    public void givenTwoCastMembersAndOnePersisted_whenCallsExistsById_shouldReturnPersisted() {
        final var aCastMember = CastMember.newMember("Vind", CastMemberType.DIRECTOR);

        final var expectedId = aCastMember.getId();
        final var expectedItems = 1;

        Assertions.assertEquals(0, castMemberRepository.count());

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aCastMember));

        final var actualItems = castMemberGateway.existsByIds(
                List.of(
                        CastMemberID.from("123"),
                expectedId));

        Assertions.assertEquals(expectedItems, actualItems.size());
        Assertions.assertEquals(expectedId.getValue(), actualItems.get(0).getValue());
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteById_shouldIgnore() {
        // given
        final var expectedName = name();
        final var expectedType = type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        //when
        castMemberGateway.deleteById(CastMemberID.from("123"));

        //then
        Assertions.assertEquals(1, castMemberRepository.count());
    }

    @Test
    public void givenAValidCastMember_whenCallsFindById_shouldReturnIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();
        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        //when
        final var actualMember = castMemberGateway.findById(expectedId).get();

        //then
        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsFindById_shouldReturnEmpty() {
        // given
        final var aMember = CastMember.newMember(name(), type());
        final var expectedId = aMember.getId();

        castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        //when
        final var actualMember = castMemberGateway.findById(CastMemberID.from("123"));

        //then
        Assertions.assertTrue(actualMember.isEmpty());
    }

    @Test
    public void givenEmptyCastMembers_whenCallsFindAll_shouldReturnEmpty() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var aQuery = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        // when
        final var actualPage = castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedTotal, actualPage.items().size());
    }

    @ParameterizedTest
    @CsvSource({
            "vin,0,10,1,1,Vin Diesel",
            "tara,0,10,1,1,Quentin Tarantino",
            "jas,0,10,1,1,Jason Momoa",
            "MAR,0,10,1,1,Martin Scorcese",
            "har,0,10,1,1,Kit Harington",
    })
    public void givenAValidTerm_whenCallsFindAll_shouldReturnFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final int expectedTotal,
            final String expectedName
    ) {
        // given
        mockMembers();

        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualPage = castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());
        Assertions.assertEquals(expectedName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,5,5,Jason Momoa",
            "name,desc,0,10,5,5,Vin Diesel",
            "createdAt,asc,0,10,5,5,Kit Harington",
            "createdAt,desc,0,10,5,5,Martin Scorcese",
    })
    public void givenAValidSortAndDirection_whenCallsFindAll_shouldReturnSorted(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final int expectedTotal,
            final String expectedName
    ) {
        // given
        mockMembers();

        final var expectedTerms = "";

        final var aQuery = new SearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualPage = castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());
        Assertions.assertEquals(expectedName, actualPage.items().get(0).getName());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,5,Jason Momoa;Kit Harington",
            "1,2,2,5,Martin Scorcese;Quentin Tarantino",
            "2,2,1,5,Vin Diesel",
    })
    public void givenAValidPagination_whenCallsFindAll_shouldReturnPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final int expectedTotal,
            final String expectedNames
    ) {
        // given
        mockMembers();

        final var expectedSort = "name";
        final var expectedDiretion = "asc";
        final var expectedTerms = "";

        final var aQuery = new SearchQuery(expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDiretion);

        // when
        final var actualPage = castMemberGateway.findAll(aQuery);

        // then
        Assertions.assertEquals(expectedPage, actualPage.currentPage());
        Assertions.assertEquals(expectedPerPage, actualPage.perPage());
        Assertions.assertEquals(expectedTotal, actualPage.total());
        Assertions.assertEquals(expectedItemsCount, actualPage.items().size());

        int index = 0;

        for (final var expectedName : expectedNames.split(";")) {
            Assertions.assertEquals(expectedName, actualPage.items().get(index).getName());
            index++;
        }
    }

    private void mockMembers() {
        castMemberRepository.saveAllAndFlush(List.of(
                CastMemberJpaEntity.from(CastMember.newMember("Kit Harington", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Vin Diesel", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Quentin Tarantino", CastMemberType.DIRECTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Jason Momoa", CastMemberType.ACTOR)),
                CastMemberJpaEntity.from(CastMember.newMember("Martin Scorcese", CastMemberType.DIRECTOR))
        ));
    }
}