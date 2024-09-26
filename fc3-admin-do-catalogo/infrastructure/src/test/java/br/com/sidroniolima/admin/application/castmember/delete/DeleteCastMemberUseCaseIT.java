package br.com.sidroniolima.admin.application.castmember.delete;

import br.com.sidroniolima.admin.Fixture;
import br.com.sidroniolima.admin.IntegrationTest;
import br.com.sidroniolima.admin.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import br.com.sidroniolima.admin.domain.castmember.CastMember;
import br.com.sidroniolima.admin.domain.castmember.CastMemberGateway;
import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.infrastructure.castmember.persistence.CastMemberJpaEntity;
import br.com.sidroniolima.admin.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@IntegrationTest
public class DeleteCastMemberUseCaseIT {

    @Autowired
    private DeleteCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    public void givenAValidId_whenCallsDeleteCastMember_shouldDeleteId() {
        // given
        final var aMemberOne = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());
        final var expectedId = aMemberOne.getId();
        final var aMemberTwo = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMemberOne));
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMemberTwo));

        Assertions.assertEquals(2, this.castMemberRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, this.castMemberRepository.count());
        Assertions.assertFalse(this.castMemberRepository.existsById(expectedId.getValue()));
        Assertions.assertTrue(this.castMemberRepository.existsById(aMemberTwo.getId().getValue()));
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        // given
        final var expectedId = CastMemberID.from("123");

        this.castMemberRepository.saveAndFlush(
                CastMemberJpaEntity.from(
                        CastMember.newMember(Fixture.name(), Fixture.CastMember.type())
                )
        );

        Assertions.assertEquals(1, this.castMemberRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, this.castMemberRepository.count());
    }

    @Test
    public void givenAValidId_whenCallsDeleteCastMemberAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMember.type());
        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, this.castMemberRepository.count());

        doThrow(new IllegalStateException("Gateway error"))
                .when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));
        Assertions.assertEquals(1, this.castMemberRepository.count());
    }
}
