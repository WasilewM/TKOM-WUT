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

public class LexerDataTypesTest {
    @ParameterizedTest
    @MethodSource("generateIntegerData")
    void lexInteger(SingleTokenTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.inputString().getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        Token token = lex.lexToken();
        assertEquals(testScenarioParams.token().tokenType(), token.getTokenType());
        assertEquals(testScenarioParams.token().value(), token.getValue());
        assertEquals(testScenarioParams.token().lineNumber(), token.getPosition().getLineNumber());
        assertEquals(testScenarioParams.token().columnNumber(), token.getPosition().getColumnNumber());
    }

    static Stream<Arguments> generateIntegerData() {
        return Stream.of(
          Arguments.of(new SingleTokenTestParams("0", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 0, 1, 1))),
          Arguments.of(new SingleTokenTestParams("1", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 1, 1, 1))),
          Arguments.of(new SingleTokenTestParams("7", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 7, 1, 1))),
          Arguments.of(new SingleTokenTestParams("1023", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 1023, 1, 1))),
          Arguments.of(new SingleTokenTestParams("        10", new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 10, 1, 9)))
        );
    }

    @Test
    void lexDoubleGreaterThanOne() {
        InputStream inputStream = new ByteArrayInputStream("92.456".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_VALUE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(92.456, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
    @Test
    void lexDoubleCloseToOne() {
        InputStream inputStream = new ByteArrayInputStream("1.0012".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_VALUE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(1.0012, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleOfValueBetweenZeroAndOne() {
        InputStream inputStream = new ByteArrayInputStream("0.00054".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_VALUE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(0.00054, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleAfterWhitespacesAndNewline() {
        InputStream inputStream = new ByteArrayInputStream("   \n 103.72\n".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_VALUE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(103.72, token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleAfterMultipleWhitespacesAndNewlines() {
        InputStream inputStream = new ByteArrayInputStream("   \n \n\n    103.72\n".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_VALUE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(103.72, token.getValue());
        assertEquals(4, token.getPosition().getLineNumber());
        assertEquals(5, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSingleLetterString() {
        InputStream inputStream = new ByteArrayInputStream("\"a\"".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.STRING_VALUE, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("a", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSingleLetterStringWithUnclosedQuotes() {
        InputStream inputStream = new ByteArrayInputStream("\"a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token errorToken = lex.lexToken();

        assertEquals(TokenTypeEnum.UNCLOSED_QUOTES_ERROR, errorToken.getTokenType());
        assertEquals(StringToken.class, errorToken.getClass());
        assertEquals("a", errorToken.getValue());
        assertEquals(1, errorToken.getPosition().getLineNumber());
        assertEquals(1, errorToken.getPosition().getColumnNumber());
    }

    @Test
    void lexBoolTrueValue() {
        InputStream inputStream = new ByteArrayInputStream("True".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexBoolFalseValue() {
        InputStream inputStream = new ByteArrayInputStream("False".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
