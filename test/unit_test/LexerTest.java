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
    }

    @Test
    void lexSingleLetterIdentifier() {
        InputStream inputStream = new ByteArrayInputStream("a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("a", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSingleLetterAndSingleDigitIdentifier() {
        InputStream inputStream = new ByteArrayInputStream("a4".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("a4", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexMultipleLettersAndDigitsIdentifier() {
        InputStream inputStream = new ByteArrayInputStream("a4b6_c7".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("a4b6_c7", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIdentifierWithUnderscores() {
        InputStream inputStream = new ByteArrayInputStream("hello_There_Identifier".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("hello_There_Identifier", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIdentifierAfterWhitespacesAndNewlines() {
        InputStream inputStream = new ByteArrayInputStream("\n\n\n   \n\n\n hello_There_Identifier\n".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("hello_There_Identifier", token.getValue());
        assertEquals(7, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIdentifierLongerThanAllowedLength() {
        InputStream inputStream = new ByteArrayInputStream("thisIsSomeVeryLongIdentifier_12345678".getBytes());
        int identifierMaxLength = 14;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        lex.setIdentifierMaxLength(identifierMaxLength);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.IDENTIFIER_EXCEEDED_MAXIMUM_LENGTH_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("thisIsSomeVery", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexComment() {
        InputStream inputStream = new ByteArrayInputStream("#hello there from comment".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.COMMENT, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("#hello there from comment", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexCommentAfterWhitespacesAndNewlines() {
        InputStream inputStream = new ByteArrayInputStream("\n \n  \n\n #hello there from comment".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.COMMENT, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("#hello there from comment", token.getValue());
        assertEquals(5, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexErrorWhenInvalidCharacter() {
        InputStream inputStream = new ByteArrayInputStream("|".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.UNKNOWN_CHAR_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("|", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
