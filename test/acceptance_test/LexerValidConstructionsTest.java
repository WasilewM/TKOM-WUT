import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class LexerValidConstructionsTest {

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
    void lexTokensFromValidProgram() {
        InputStream inputStream = new ByteArrayInputStream(("""
                Int main() {
                Double a = 4.01 / 2.67;
                return 0;
                }""").getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        Token token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.INT_KEYWORD);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.MAIN_KEYWORD);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(5, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.LEFT_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(9, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RIGHT_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(10, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.LEFT_CURLY_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(12, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.DOUBLE_KEYWORD);
        assertNull(token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("a", token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(8, token.getPosition().getColumnNumber());

        Token assignmentToken = lex.lexToken();
        assertEquals(StringToken.class, assignmentToken.getClass());
        assertEquals(assignmentToken.getTokenType(), TokenTypeEnum.ASSIGNMENT_OPERATOR);
        assertNull(assignmentToken.getValue());
        assertEquals(2, assignmentToken.getPosition().getLineNumber());
        assertEquals(10, assignmentToken.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.DOUBLE);
        assertEquals(4.01, token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(12, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.DIVISION_OPERATOR);
        assertNull(token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(17, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.DOUBLE);
        assertEquals(2.67, token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(19, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.SEMICOLON);
        assertNull(token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(23, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RETURN_KEYWORD);
        assertNull(token.getValue());
        assertEquals(3, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(IntegerToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.INTEGER);
        assertEquals(0, token.getValue());
        assertEquals(3, token.getPosition().getLineNumber());
        assertEquals(8, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.SEMICOLON);
        assertNull(token.getValue());
        assertEquals(3, token.getPosition().getLineNumber());
        assertEquals(9, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RIGHT_CURLY_BRACKET);
        assertNull(token.getValue());
        assertEquals(4, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexWhileStatementTest() {
        InputStream inputStream = new ByteArrayInputStream(("while (a<=b && (c != d || e == f)) { return True; }").getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        Token token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.WHILE_KEYWORD);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.LEFT_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(7, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("a", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(8, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(9, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("b", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(11, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.AND_OPERATOR);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(13, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.LEFT_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(16, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("c", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(17, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.NOT_EQUAL_OPERATOR);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(19, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("d", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(22, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.OR_OPERATOR);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(24, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("e", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(27, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.EQUAL_OPERATOR);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(29, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.IDENTIFIER);
        assertEquals("f", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(32, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RIGHT_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(33, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RIGHT_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(34, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.LEFT_CURLY_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(36, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RETURN_KEYWORD);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(38, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.BOOL_TRUE_VALUE);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(45, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.SEMICOLON);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(49, token.getPosition().getColumnNumber());

        token = lex.lexToken();
        assertEquals(StringToken.class, token.getClass());
        assertEquals(token.getTokenType(), TokenTypeEnum.RIGHT_CURLY_BRACKET);
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(51, token.getPosition().getColumnNumber());
    }
}
