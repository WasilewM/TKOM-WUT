package lexer.unit_test;

import lexer.Lexer;
import lexer.TokenTypeEnum;
import lexer.tokens.DoubleToken;
import lexer.tokens.IntegerToken;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import lexer.utils.SingleTokenDescription;
import lexer.utils.SingleTokenTestParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerDataTypesTest {
    private static void performTest(SingleTokenTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.inputString().getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);

        Token token = lex.lexToken();
        assertEquals(testScenarioParams.token().getTokenType(), token.getTokenType());
        assertEquals(testScenarioParams.token().getValue(), token.getValue());
        assertEquals(testScenarioParams.token().getLineNumber(), token.getPosition().getLineNumber());
        assertEquals(testScenarioParams.token().getColumnNumber(), token.getPosition().getColumnNumber());
    }

    static Stream<Arguments> generateIntTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("0", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 0, 1, 1))),
                Arguments.of(new SingleTokenTestParams("1", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 1, 1, 1))),
                Arguments.of(new SingleTokenTestParams("7", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 7, 1, 1))),
                Arguments.of(new SingleTokenTestParams("1023", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 1023, 1, 1))),
                Arguments.of(new SingleTokenTestParams("        10", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 10, 1, 9)))
        );
    }

    static Stream<Arguments> generateDoubleTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("92.456", new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 92.456, 1, 1))),
                Arguments.of(new SingleTokenTestParams("1.0012", new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 1.0012, 1, 1))),
                Arguments.of(new SingleTokenTestParams("0.00054", new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 0.00054, 1, 1))),
                Arguments.of(new SingleTokenTestParams("   \n 103.72\n", new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 103.72, 2, 2))),
                Arguments.of(new SingleTokenTestParams("   \n \n\n    103.72\n", new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 103.72, 4, 5)))
        );
    }

    static Stream<Arguments> generateStringTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("\"a\"", new SingleTokenDescription(TokenTypeEnum.STRING_VALUE, "a", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\"a", new SingleTokenDescription(TokenTypeEnum.UNCLOSED_QUOTES_ERROR, "a", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\"a\n\n\n\n   b\n\nc\"", new SingleTokenDescription(TokenTypeEnum.STRING_VALUE, "a\n\n\n\n   b\n\nc", 1, 1)))
        );
    }

    static Stream<Arguments> generateStringWithEscapingTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("\"t\\\"_S2\"", new SingleTokenDescription(TokenTypeEnum.STRING_VALUE, "t\"_S2", 1, 1))),
                Arguments.of(new SingleTokenTestParams("\"t\\\"", new SingleTokenDescription(TokenTypeEnum.UNCLOSED_QUOTES_ERROR, "t\"", 1, 1)))
        );
    }

    static Stream<Arguments> generateBoolTokensData() {
        return Stream.of(
                Arguments.of(new SingleTokenTestParams("True", new SingleTokenDescription(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD, null, 1, 1))),
                Arguments.of(new SingleTokenTestParams("False", new SingleTokenDescription(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD, null, 1, 1)))
        );
    }

    @ParameterizedTest
    @MethodSource("generateIntTokensData")
    void lexIntValue(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @ParameterizedTest
    @MethodSource("generateDoubleTokensData")
    void lexDoubleValue(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @ParameterizedTest
    @MethodSource("generateStringTokensData")
    void lexStringValue(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @ParameterizedTest
    @MethodSource("generateStringWithEscapingTokensData")
    void lexStringWithEscaping(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @ParameterizedTest
    @MethodSource("generateBoolTokensData")
    void lexBoolValue(SingleTokenTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    @Test
    void lexStringLongerThanAllowedLength() {
        InputStream inputStream = new ByteArrayInputStream("\"thisIsSomeLongLongString\"".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        int stringMaxLength = 10;
        lex.setStringMaxLength(stringMaxLength);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.STRING_EXCEEDED_MAXIMUM_LENGTH_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("thisIsSome", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLongStringWithEscaping() {
        InputStream inputStream = new ByteArrayInputStream("\"thisIsSome\\\"String\\\\WithEscaping\"".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.STRING_VALUE, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("thisIsSome\"String\\WithEscaping", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLongStringWithNewline() {
        InputStream inputStream = new ByteArrayInputStream("\"thisIsSome\\\n\"".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.STRING_VALUE, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("thisIsSome\n", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }


    @Test
    void lexIntGreaterMaxInt() {
        InputStream inputStream = new ByteArrayInputStream("2147483649".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.INT_EXCEEDED_RANGE_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("214748364", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIntGreaterMaxIntFollowedByValidInt() {
        InputStream inputStream = new ByteArrayInputStream("21474836491213".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken stringToken = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.INT_EXCEEDED_RANGE_ERROR, stringToken.getTokenType());
        assertEquals(StringToken.class, stringToken.getClass());
        assertEquals("214748364", stringToken.getValue());
        assertEquals(1, stringToken.getPosition().getLineNumber());
        assertEquals(1, stringToken.getPosition().getColumnNumber());

        IntegerToken intToken = (IntegerToken) lex.lexToken();
        assertEquals(TokenTypeEnum.INT_VALUE, intToken.getTokenType());
        assertEquals(IntegerToken.class, intToken.getClass());
        assertEquals(91213, intToken.getValue());
        assertEquals(1, intToken.getPosition().getLineNumber());
        assertEquals(10, intToken.getPosition().getColumnNumber());
    }

    @Test
    void lexIntGreaterThanAllowed() {
        InputStream inputStream = new ByteArrayInputStream("11".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        int maxInt = 10;
        lex.setMaxInt(maxInt);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.INT_EXCEEDED_RANGE_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("1", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleGreaterThanAllowed() {
        InputStream inputStream = new ByteArrayInputStream("10.1".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        double maxDouble = 10.0;
        lex.setMaxDouble(maxDouble);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_EXCEEDED_RANGE_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("10.0", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleMalformed() {
        InputStream inputStream = new ByteArrayInputStream("10.".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        DoubleToken token = (DoubleToken) lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_VALUE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(10.0, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
