import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LexerInvalidConstructionsTest {
    @Test
    void lexErrorTokenWhenOrOperatorIsMalformedAndIsFollowedByValidMultiplicationOperator() {
        InputStream inputStream = new ByteArrayInputStream("|*".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token errorToken = lex.lexToken();

        assertEquals(TokenTypeEnum.UNKNOWN_CHAR_ERROR, errorToken.getTokenType());
        assertEquals(StringToken.class, errorToken.getClass());
        assertEquals("|", errorToken.getValue());
        assertEquals(1, errorToken.getPosition().getLineNumber());
        assertEquals(1, errorToken.getPosition().getColumnNumber());

        Token token = lex.lexToken();
        assertEquals(TokenTypeEnum.MULTIPLICATION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexErrorTokenWhenAndAndOperatorIsMalformedAndIsFollowedByValidSubtractionOperator() {
        InputStream inputStream = new ByteArrayInputStream("&-".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token errorToken = lex.lexToken();

        assertEquals(TokenTypeEnum.UNKNOWN_CHAR_ERROR, errorToken.getTokenType());
        assertEquals(StringToken.class, errorToken.getClass());
        assertEquals("&", errorToken.getValue());
        assertEquals(1, errorToken.getPosition().getLineNumber());
        assertEquals(1, errorToken.getPosition().getColumnNumber());

        Token token = lex.lexToken();
        assertEquals(TokenTypeEnum.SUBTRACTION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }
}