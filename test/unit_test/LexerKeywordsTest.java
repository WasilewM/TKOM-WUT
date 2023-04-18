import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerKeywordsTest {

    @ParameterizedTest
    @MethodSource("generateKeywordTokensData")
    void lexKeyword(SingleTokenTestParams testScenarioParams) {
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

    static Stream<Arguments> generateKeywordTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("Int", new SingleTokenDescription(TokenTypeEnum.INT_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("Double", new SingleTokenDescription(TokenTypeEnum.DOUBLE_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("String", new SingleTokenDescription(TokenTypeEnum.STRING_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("Point", new SingleTokenDescription(TokenTypeEnum.POINT_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("Section", new SingleTokenDescription(TokenTypeEnum.SECTION_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("Figure", new SingleTokenDescription(TokenTypeEnum.FIGURE_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("Scene", new SingleTokenDescription(TokenTypeEnum.SCENE_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("Bool", new SingleTokenDescription(TokenTypeEnum.BOOL_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("List", new SingleTokenDescription(TokenTypeEnum.LIST_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("while", new SingleTokenDescription(TokenTypeEnum.WHILE_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("if", new SingleTokenDescription(TokenTypeEnum.IF_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("elseif", new SingleTokenDescription(TokenTypeEnum.ELSE_IF_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("else", new SingleTokenDescription(TokenTypeEnum.ELSE_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("main", new SingleTokenDescription(TokenTypeEnum.MAIN_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("return", new SingleTokenDescription(TokenTypeEnum.RETURN_KEYWORD, 1, 1))),
                Arguments.of(new SingleTokenTestParams("void", new SingleTokenDescription(TokenTypeEnum.VOID_KEYWORD, 1, 1)))
        );
    }
}
