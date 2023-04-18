import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerLanguageReservedSignsTest {
    @ParameterizedTest
    @MethodSource("generateReservedSignsTokensData")
    void lexIntValue(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    static Stream<Arguments> generateReservedSignsTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams(";", new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 1))),
                Arguments.of(new SingleTokenTestParams(",", new SingleTokenDescription(TokenTypeEnum.COMMA, 1, 1))),
                Arguments.of(new SingleTokenTestParams("(", new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 1))),
                Arguments.of(new SingleTokenTestParams(")", new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 1))),
                Arguments.of(new SingleTokenTestParams("[", new SingleTokenDescription(TokenTypeEnum.LEFT_SQUARE_BRACKET, 1, 1))),
                Arguments.of(new SingleTokenTestParams("]", new SingleTokenDescription(TokenTypeEnum.RIGHT_SQUARE_BRACKET,  1, 1))),
                Arguments.of(new SingleTokenTestParams("{", new SingleTokenDescription(TokenTypeEnum.LEFT_CURLY_BRACKET, 1, 1))),
                Arguments.of(new SingleTokenTestParams("}", new SingleTokenDescription(TokenTypeEnum.RIGHT_CURLY_BRACKET, 1, 1))),
                Arguments.of(new SingleTokenTestParams(".", new SingleTokenDescription(TokenTypeEnum.DOT,  1, 1)))
        );
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
}
