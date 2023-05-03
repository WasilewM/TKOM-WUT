import lexer.Lexer;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerMalformedOperatorsTest {
    @Test
    void lexErrorTokenWhenOrOperatorIsMalformedAndIsFollowedByValidMultiplicationOperator() {
        InputStream inputStream = new ByteArrayInputStream("|*".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken errorToken = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.UNKNOWN_CHAR_ERROR, errorToken.getTokenType());
        assertEquals(StringToken.class, errorToken.getClass());
        assertEquals("|", errorToken.getValue());
        assertEquals(1, errorToken.getPosition().getLineNumber());
        assertEquals(1, errorToken.getPosition().getColumnNumber());

        Token token = lex.lexToken();
        assertEquals(TokenTypeEnum.MULTIPLICATION_OPERATOR, token.getTokenType());
        assertEquals(Token.class, token.getClass());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexErrorTokenWhenAndAndOperatorIsMalformedAndIsFollowedByValidSubtractionOperator() {
        InputStream inputStream = new ByteArrayInputStream("&-".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Lexer lex = new Lexer(bufferedReader);
        StringToken errorToken = (StringToken) lex.lexToken();

        assertEquals(TokenTypeEnum.UNKNOWN_CHAR_ERROR, errorToken.getTokenType());
        assertEquals(StringToken.class, errorToken.getClass());
        assertEquals("&", errorToken.getValue());
        assertEquals(1, errorToken.getPosition().getLineNumber());
        assertEquals(1, errorToken.getPosition().getColumnNumber());

        Token token = lex.lexToken();
        assertEquals(TokenTypeEnum.SUBTRACTION_OPERATOR, token.getTokenType());
        assertEquals(Token.class, token.getClass());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }
}
