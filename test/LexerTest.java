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
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexUnknownCharacter() {
        InputStream inputStream = new ByteArrayInputStream("a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        assertNull(lex.lexToken());
    }
}
