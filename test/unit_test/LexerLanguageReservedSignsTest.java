import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerLanguageReservedSignsTest {
    @Test
    void lexSemicolon() {
        InputStream inputStream = new ByteArrayInputStream(";".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.SEMICOLON, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexComma() {
        InputStream inputStream = new ByteArrayInputStream(",".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.COMMA, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLeftBracket() {
        InputStream inputStream = new ByteArrayInputStream("(".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.LEFT_BRACKET, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexRightBracket() {
        InputStream inputStream = new ByteArrayInputStream(")".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.RIGHT_BRACKET, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLeftSquareBracket() {
        InputStream inputStream = new ByteArrayInputStream("[".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.LEFT_SQUARE_BRACKET, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexRightSquareBracket() {
        InputStream inputStream = new ByteArrayInputStream("]".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.RIGHT_SQUARE_BRACKET, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLeftCurlyBracket() {
        InputStream inputStream = new ByteArrayInputStream("{".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.LEFT_CURLY_BRACKET, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexRightCurlyBracket() {
        InputStream inputStream = new ByteArrayInputStream("}".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.RIGHT_CURLY_BRACKET, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDot() {
        InputStream inputStream = new ByteArrayInputStream(".".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOT, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
