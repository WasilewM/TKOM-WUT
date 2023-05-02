import lexer.Lexer;
import lexer.tokens.StringToken;
import lexer.TokenTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.SingleTokenDescription;
import utils.SingleTokenTestParams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerIdentifierTest {

    @ParameterizedTest
    @MethodSource("generateIdentifierTokensData")
    void lexIdentifier(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @ParameterizedTest
    @MethodSource("generateIdentifierLookingSimilarToKeywordTokensData")
    void lexIdentifierLookingSimilarToKeywords(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    private static void performTest(SingleTokenTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.inputString().getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);

        StringToken token = (StringToken) lex.lexToken();
        assertEquals(testScenarioParams.token().getTokenType(), token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals(testScenarioParams.token().getValue(), token.getValue());
        assertEquals(testScenarioParams.token().getLineNumber(), token.getPosition().getLineNumber());
        assertEquals(testScenarioParams.token().getColumnNumber(), token.getPosition().getColumnNumber());
    }

    static Stream<Arguments> generateIdentifierTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("a", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 1, 1))),
                Arguments.of(new SingleTokenTestParams("a4", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a4", 1, 1))),
                Arguments.of(new SingleTokenTestParams("a4b6_c7", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a4b6_c7", 1, 1))),
                Arguments.of(new SingleTokenTestParams("hello_There_Identifier", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "hello_There_Identifier", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\n\n\n   \n\n\n hello_There_Identifier\n", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "hello_There_Identifier", 7, 2)))
        );
    }

    static Stream<Arguments> generateIdentifierLookingSimilarToKeywordTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("Integer", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "Integer", 1, 1))),
                Arguments.of(new SingleTokenTestParams("myDouble", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "myDouble", 1, 1))),
                Arguments.of(new SingleTokenTestParams("some_String", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "some_String", 1, 1))),
                Arguments.of(new SingleTokenTestParams("main", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "main", 1, 1))),  // main is not a keyword, but it's easy to expect that ii is a keyword
                Arguments.of(new SingleTokenTestParams("firstPoint", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "firstPoint", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\n\nPointlessValue", new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "PointlessValue", 3, 1)))
        );
    }

    @Test
    void lexIdentifierLongerThanAllowedLength() {
        InputStream inputStream = new ByteArrayInputStream("thisIsSomeVeryLongIdentifier_12345678".getBytes());
        int identifierMaxLength = 14;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        lex.setIdentifierMaxLength(identifierMaxLength);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER_EXCEEDED_MAXIMUM_LENGTH_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("thisIsSomeVery", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
