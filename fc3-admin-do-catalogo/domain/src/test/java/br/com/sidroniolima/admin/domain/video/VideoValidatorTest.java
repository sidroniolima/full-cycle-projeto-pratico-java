package br.com.sidroniolima.admin.domain.video;

import br.com.sidroniolima.admin.domain.castmember.CastMemberID;
import br.com.sidroniolima.admin.domain.category.CategoryID;
import br.com.sidroniolima.admin.domain.exceptions.DomainException;
import br.com.sidroniolima.admin.domain.genre.GenreID;
import br.com.sidroniolima.admin.domain.validation.ValidationHandler;
import br.com.sidroniolima.admin.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.Set;

public class VideoValidatorTest {

    @Test
    public void givenNullTitle_whenCallsValidate_shouldReceiveError() {
        // given
        final String expectedTitle = null;
        final var expectedDescription = """
                A Description
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenEmptyTitle_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "";
        final var expectedDescription = """
                A Description
                """;
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenTitleWithLengthGreaterThan255_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = """
        A prática cotidiana prova que o surgimento do comércio virtual nos obriga à análise das condições financeiras e
        administrativas exigidas. Desta maneira, a necessidade de renovação processual cumpre um papel essencial na
        formulação das posturas dos órgãos dirigentes com relação às suas atribuições. Por conseguinte, a mobilidade
        dos capitais internacionais auxilia a preparação e a composição dos modos de operação convencionais.
        No entanto, não podemos esquecer que a consolidação das estruturas possibilita uma melhor visão global do
            sistema de formação de quadros que corresponde às necessidades.
                """;

        final var expectedDescription = """
        A prática cotidiana prova que o surgimento do comércio virtual nos obriga à análise das condições financeiras e
        administrativas exigidas. Desta maneira, a necessidade de renovação processual cumpre um papel essencial na
        formulação das posturas dos órgãos dirigentes com relação às suas atribuições. Por conseguinte, a mobilidade
        dos capitais internacionais auxilia a preparação e a composição dos modos de operação convencionais.
        No entanto, não podemos esquecer que a consolidação das estruturas possibilita uma melhor visão global do
            sistema de formação de quadros que corresponde às necessidades.
                """;

        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenAnEmptyDescription_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Inverviews";
        final var expectedDescription = "";
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'description' should not be empty";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenDescriptionWithLengthGreaterThan400_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Inverviews";

        final var expectedDescription = """
            A certificação de metodologias que nos auxiliam a lidar com o entendimento das metas propostas facilita a criação das regras de conduta normativas. Podemos já vislumbrar o modo pelo qual a estrutura atual da organização desafia a capacidade de equalização dos métodos utilizados na avaliação de resultados. Não obstante, o início da atividade geral de formação de atitudes aponta para a melhoria do fluxo de informações. Neste sentido, o aumento do diálogo entre os diferentes setores produtivos faz parte de um processo de gerenciamento do processo de comunicação como um todo.                
            Todavia, a complexidade dos estudos efetuados é uma das consequências dos índices pretendidos. Caros amigos, a revolução dos costumes promove a alavancagem do levantamento das variáveis envolvidas. É importante questionar o quanto a necessidade de renovação processual estende o alcance e a importância das novas proposições.    
            Nunca é demais lembrar o peso e o significado destes problemas, uma vez que a contínua expansão de nossa atividade causa impacto indireto na reavaliação das posturas dos órgãos dirigentes com relação às suas atribuições. Assim mesmo, o comprometimento entre as equipes deve passar por modificações independentemente do retorno esperado a longo prazo. No mundo atual, a consulta aos diversos militantes obstaculiza a apreciação da importância dos relacionamentos verticais entre as hierarquias. Acima de tudo, é fundamental ressaltar que a determinação clara de objetivos acarreta um processo de reformulação e modernização das formas de ação.
            A nível organizacional, o surgimento do comércio virtual não pode mais se dissociar das diretrizes de desenvolvimento para o futuro. O incentivo ao avanço tecnológico, assim como a hegemonia do ambiente político ainda não demonstrou convincentemente que vai participar na mudança do impacto na agilidade decisória. Percebemos, cada vez mais, que o desafiador cenário globalizado possibilita uma melhor visão global das condições inegavelmente apropriadas. Pensando mais a longo prazo, a constante divulgação das informações apresenta tendências no sentido de aprovar a manutenção dos conhecimentos estratégicos para atingir a excelência. As experiências acumuladas demonstram que a percepção das dificuldades talvez venha a ressaltar a relatividade das direções preferenciais no sentido do progresso.    
            Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se o desenvolvimento contínuo de distintas formas de atuação maximiza as possibilidades por conta do remanejamento dos quadros funcionais. Evidentemente, a mobilidade dos capitais internacionais prepara-nos para enfrentar situações atípicas decorrentes do orçamento setorial. Ainda assim, existem dúvidas a respeito de como o fenômeno da Internet auxilia a preparação e a composição de todos os recursos funcionais envolvidos. Por conseguinte, o acompanhamento das preferências de consumo representa uma abertura para a melhoria dos procedimentos normalmente adotados.
            Gostaria de enfatizar que a expansão dos mercados mundiais assume importantes posições no estabelecimento do sistema de participação geral. O que temos que ter sempre em mente é que a competitividade nas transações comerciais exige a precisão e a definição das condições financeiras e administrativas exigidas. Por outro lado, a valorização de fatores subjetivos pode nos levar a considerar a reestruturação das diversas correntes de pensamento.
            É claro que o julgamento imparcial das eventualidades nos obriga à análise dos modos de operação convencionais. No entanto, não podemos esquecer que a execução dos pontos do programa afeta positivamente a correta previsão dos níveis de motivação departamental. Do mesmo modo, o novo modelo estrutural aqui preconizado garante a contribuição de um grupo importante na determinação da gestão inovadora da qual fazemos parte. As experiências acumuladas demonstram que o desafiador cenário globalizado afeta positivamente a correta previsão do investimento em reciclagem técnica. A nível organizacional, a execução dos pontos do programa apresenta tendências no sentido de aprovar a manutenção dos paradigmas corporativos.
        """;

        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenNullLaunchedAt_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Inverviews";
        final var expectedDescription = "A description";
        final Year expectedLaunchedAt = null;
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final var expectedRating = Rating.L;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'launchedAt' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }

    @Test
    public void givenNullRating_whenCallsValidate_shouldReceiveError() {
        // given
        final var expectedTitle = "System Design Inverviews";
        final var expectedDescription = "A description";
        final var expectedLaunchedAt = Year.of(2024);
        final var expectedDuration = 120.10;
        final var expectedOpened = false;
        final var expectedPublished = false;
        final Rating expectedRating = null;
        final var expectedCategories = Set.of(CategoryID.unique());
        final var expectedGenres = Set.of(GenreID.unique());
        final var expectedMembers = Set.of(CastMemberID.unique());

        final var expectedErrorCount = 0;
        final var expectedErrorMessage = "'rating' should not be null";

        final var actualVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );

        final var validator =
                new VideoValidator(actualVideo, new ThrowsValidationHandler());

        // when
        final var actualError = Assertions.assertThrows(
                DomainException.class,
                () -> validator.validate());

        // then

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
    }
}
