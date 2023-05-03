import lexer.Lexer;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest {
    @Test
    void lexComment() {
        InputStream inputStream = new ByteArrayInputStream("#hello there from comment".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.COMMENT, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("#hello there from comment", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexCommentAfterWhitespacesAndNewlines() {
        InputStream inputStream = new ByteArrayInputStream("\n \n  \n\n #hello there from comment".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.COMMENT, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("#hello there from comment", token.getValue());
        assertEquals(5, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexErrorWhenInvalidCharacter() {
        InputStream inputStream = new ByteArrayInputStream("|".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken token = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.UNKNOWN_CHAR_ERROR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("|", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
}
