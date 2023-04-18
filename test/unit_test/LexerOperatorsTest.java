import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerOperatorsTest {
    @ParameterizedTest
    @MethodSource("generateOperatorTokensData")
    void lexOperator(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    private static void performTest(SingleTokenTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.inputString().getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        Token token = lex.lexToken();
        assertEquals(testScenarioParams.token().getTokenType(), token.getTokenType());
        assertNull(token.getValue());
        assertEquals(testScenarioParams.token().getLineNumber(), token.getPosition().getLineNumber());
        assertEquals(testScenarioParams.token().getColumnNumber(), token.getPosition().getColumnNumber());
    }

    static Stream<Arguments> generateOperatorTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("+", new SingleTokenDescription(TokenTypeEnum.ADDITION_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("-", new SingleTokenDescription(TokenTypeEnum.SUBTRACTION_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("*", new SingleTokenDescription(TokenTypeEnum.MULTIPLICATION_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("/", new SingleTokenDescription(TokenTypeEnum.DIVISION_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("//", new SingleTokenDescription(TokenTypeEnum.DISCRETE_DIVISION_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("=", new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("\n\n \n     =", new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 4, 6))),
                Arguments.of(new SingleTokenTestParams("==", new SingleTokenDescription(TokenTypeEnum.EQUAL_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("!=", new SingleTokenDescription(TokenTypeEnum.NOT_EQUAL_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("<", new SingleTokenDescription(TokenTypeEnum.LESS_THAN_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("<=", new SingleTokenDescription(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams(">", new SingleTokenDescription(TokenTypeEnum.GREATER_THAN_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams(">=", new SingleTokenDescription(TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("&&", new SingleTokenDescription(TokenTypeEnum.AND_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("||", new SingleTokenDescription(TokenTypeEnum.OR_OPERATOR, 1, 1))),
                Arguments.of(new SingleTokenTestParams("!", new SingleTokenDescription(TokenTypeEnum.NEGATION_OPERATOR, 1, 1)))
        );
    }

    @Test
    void lexDivisionOperatorWhenDivisionFollowedByOtherChar() {
        InputStream inputStream = new ByteArrayInputStream("/a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DIVISION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexTokenWhenNotEqualOperatorIsMalformedButNegationOperatorIsValid() {
        InputStream inputStream = new ByteArrayInputStream("!-".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.NEGATION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
