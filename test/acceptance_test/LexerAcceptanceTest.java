import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class LexerAcceptanceTest {

    @Test
    void lexNotEqualOperatorFollowedByIdentifier() {
        InputStream inputStream = new ByteArrayInputStream("!=a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        Token firstToken = lex.lexToken();
        assertEquals(NotEqualOperatorToken.class, firstToken.getClass());
        assertEquals("!=", firstToken.getValue());
        assertEquals(1, firstToken.getPosition().getLineNumber());
        assertEquals(1, firstToken.getPosition().getColumnNumber());

        Token secondToken = lex.lexToken();
        assertEquals(StringToken.class, secondToken.getClass());
        assertEquals("a", secondToken.getValue());
        assertEquals(1, secondToken.getPosition().getLineNumber());
        assertEquals(3, secondToken.getPosition().getColumnNumber());
    }
}
