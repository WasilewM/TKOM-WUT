import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerDataTypesTest {
    @Test
    void lexSingleDigitInteger() {
        InputStream inputStream = new ByteArrayInputStream("1".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.INTEGER, token.getTokenType());
        assertEquals(IntegerToken.class, token.getClass());
        assertEquals(1, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexMultipleDigitsInteger() {
        InputStream inputStream = new ByteArrayInputStream("1023".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.INTEGER, token.getTokenType());
        assertEquals(IntegerToken.class, token.getClass());
        assertEquals(1023, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIntegerAfterWhitespaces() {
        InputStream inputStream = new ByteArrayInputStream("        10".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.INTEGER, token.getTokenType());
        assertEquals(IntegerToken.class, token.getClass());
        assertEquals(10, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(9, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleGreaterThanOne() {
        InputStream inputStream = new ByteArrayInputStream("92.456".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
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

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
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

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
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

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
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

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
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

        assertEquals(TokenTypeEnum.STRING, token.getTokenType());
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

        assertEquals(TokenTypeEnum.BOOL_TRUE_VALUE, token.getTokenType());
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

        assertEquals(TokenTypeEnum.BOOL_FALSE_VALUE, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
