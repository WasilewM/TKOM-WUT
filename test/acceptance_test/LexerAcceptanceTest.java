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
        assertEquals(StringToken.class, firstToken.getClass());
        assertEquals(firstToken.getTokenType(), TokenTypeEnum.NOT_EQUAL_OPERATOR);
        assertNull(firstToken.getValue());
        assertEquals(1, firstToken.getPosition().getLineNumber());
        assertEquals(1, firstToken.getPosition().getColumnNumber());

        Token secondToken = lex.lexToken();
        assertEquals(StringToken.class, secondToken.getClass());
        assertEquals(secondToken.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("a", secondToken.getValue());
        assertEquals(1, secondToken.getPosition().getLineNumber());
        assertEquals(3, secondToken.getPosition().getColumnNumber());
    }

    @Test
    void lexIntVariableAssignmentTokens() {
        InputStream inputStream = new ByteArrayInputStream("Int a = 4 - 9;".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        Token dataTypeToken = lex.lexToken();
        assertEquals(StringToken.class, dataTypeToken.getClass());
        assertEquals(dataTypeToken.getTokenType(), TokenTypeEnum.INT_KEYWORD);
        assertNull(dataTypeToken.getValue());
        assertEquals(1, dataTypeToken.getPosition().getLineNumber());
        assertEquals(1, dataTypeToken.getPosition().getColumnNumber());

        Token identifierToken = lex.lexToken();
        assertEquals(StringToken.class, identifierToken.getClass());
        assertEquals(identifierToken.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("a", identifierToken.getValue());
        assertEquals(1, identifierToken.getPosition().getLineNumber());
        assertEquals(5, identifierToken.getPosition().getColumnNumber());

        Token assignmentToken = lex.lexToken();
        assertEquals(StringToken.class, assignmentToken.getClass());
        assertEquals(assignmentToken.getTokenType(), TokenTypeEnum.ASSIGNMENT_OPERATOR);
        assertNull(assignmentToken.getValue());
        assertEquals(1, assignmentToken.getPosition().getLineNumber());
        assertEquals(7, assignmentToken.getPosition().getColumnNumber());

        Token valueToken = lex.lexToken();
        assertEquals(IntegerToken.class, valueToken.getClass());
        assertEquals(valueToken.getTokenType(), TokenTypeEnum.INTEGER);
        assertEquals(4, valueToken.getValue());
        assertEquals(1, valueToken.getPosition().getLineNumber());
        assertEquals(9, valueToken.getPosition().getColumnNumber());

        Token subtractionOperatorToken = lex.lexToken();
        assertEquals(StringToken.class, subtractionOperatorToken.getClass());
        assertEquals(subtractionOperatorToken.getTokenType(), TokenTypeEnum.SUBTRACTION_OPERATOR);
        assertNull(subtractionOperatorToken.getValue());
        assertEquals(1, subtractionOperatorToken.getPosition().getLineNumber());
        assertEquals(11, subtractionOperatorToken.getPosition().getColumnNumber());

        Token secondValueToken = lex.lexToken();
        assertEquals(IntegerToken.class, secondValueToken.getClass());
        assertEquals(secondValueToken.getTokenType(), TokenTypeEnum.INTEGER);
        assertEquals(9, secondValueToken.getValue());
        assertEquals(1, secondValueToken.getPosition().getLineNumber());
        assertEquals(13, secondValueToken.getPosition().getColumnNumber());

        Token semicolonToken = lex.lexToken();
        assertEquals(StringToken.class, semicolonToken.getClass());
        assertEquals(semicolonToken.getTokenType(), TokenTypeEnum.SEMICOLON);
        assertNull(semicolonToken.getValue());
        assertEquals(1, semicolonToken.getPosition().getLineNumber());
        assertEquals(14, semicolonToken.getPosition().getColumnNumber());
    }

    @Test
    void lexTwoIntsAdditionAndComparisonWithThirdInt() {
        InputStream inputStream = new ByteArrayInputStream("4 + 3 >= 81".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);


        Token valueToken = lex.lexToken();
        assertEquals(IntegerToken.class, valueToken.getClass());
        assertEquals(valueToken.getTokenType(), TokenTypeEnum.INTEGER);
        assertEquals(4, valueToken.getValue());
        assertEquals(1, valueToken.getPosition().getLineNumber());
        assertEquals(1, valueToken.getPosition().getColumnNumber());

        Token additionOperatorToken = lex.lexToken();
        assertEquals(StringToken.class, additionOperatorToken.getClass());
        assertEquals(additionOperatorToken.getTokenType(), TokenTypeEnum.ADDITION_OPERATOR);
        assertNull(additionOperatorToken.getValue());
        assertEquals(1, additionOperatorToken.getPosition().getLineNumber());
        assertEquals(3, additionOperatorToken.getPosition().getColumnNumber());

        Token secondValueToken = lex.lexToken();
        assertEquals(IntegerToken.class, secondValueToken.getClass());
        assertEquals(secondValueToken.getTokenType(), TokenTypeEnum.INTEGER);
        assertEquals(3, secondValueToken.getValue());
        assertEquals(1, secondValueToken.getPosition().getLineNumber());
        assertEquals(5, secondValueToken.getPosition().getColumnNumber());

        Token greaterOrEqualOperatorToken = lex.lexToken();
        assertEquals(StringToken.class, greaterOrEqualOperatorToken.getClass());
        assertEquals(greaterOrEqualOperatorToken.getTokenType(), TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR);
        assertNull(greaterOrEqualOperatorToken.getValue());
        assertEquals(1, greaterOrEqualOperatorToken.getPosition().getLineNumber());
        assertEquals(7, greaterOrEqualOperatorToken.getPosition().getColumnNumber());

        Token thirdValueToken = lex.lexToken();
        assertEquals(IntegerToken.class, thirdValueToken.getClass());
        assertEquals(thirdValueToken.getTokenType(), TokenTypeEnum.INTEGER);
        assertEquals(81, thirdValueToken.getValue());
        assertEquals(1, thirdValueToken.getPosition().getLineNumber());
        assertEquals(10, thirdValueToken.getPosition().getColumnNumber());
    }

    @Test
    void lexErrorTokenWhenOrOperatorIsMalformedAndIsFollowedByValidMultiplicationOperator() {
        InputStream inputStream = new ByteArrayInputStream("|*".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token errorToken = lex.lexToken();

        assertEquals(TokenTypeEnum.UNRECOGNISED_CHAR_ERROR, errorToken.getTokenType());
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

        assertEquals(TokenTypeEnum.UNRECOGNISED_CHAR_ERROR, errorToken.getTokenType());
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
