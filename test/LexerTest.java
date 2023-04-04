import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    @Test
    void lexerInit() {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        assertEquals(bufferedInputStream, lex.getInputStream());
        assertNull(lex.getCurrentChar());
        assertNull(lex.getNewlineConvention());
    }

    @Test
    void lexSingleDigitInteger() {
        InputStream inputStream = new ByteArrayInputStream("1".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();
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
        assertEquals(103.72, token.getValue());
        assertEquals(4, token.getPosition().getLineNumber());
        assertEquals(5, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSingleLetterIdentifier() {
        InputStream inputStream = new ByteArrayInputStream("a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();
        assertEquals("a", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIdentifierWithUnderscores() {
        InputStream inputStream = new ByteArrayInputStream("hello_There_Identifier".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();
        assertEquals("hello_There_Identifier", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIdentifierAfterWhitespacesAndNewline() {
        InputStream inputStream = new ByteArrayInputStream("\n\n\n   \n\n\n hello_There_Identifier\n".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();
        assertEquals("hello_There_Identifier", token.getValue());
        assertEquals(7, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexUnknownCharacter() {
        InputStream inputStream = new ByteArrayInputStream("!".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        assertNull(lex.lexToken());
    }
}
