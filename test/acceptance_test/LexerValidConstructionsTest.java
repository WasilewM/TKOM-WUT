import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("generateVariablesAssignmentTokensData")
    void lexVariablesAssignment(MultipleTokensTestParams testScenarioParams) {
        performTest(testScenarioParams);
    }

    private static void performTest(MultipleTokensTestParams testScenarioParams) {
        InputStream inputStream = new ByteArrayInputStream(testScenarioParams.getInputString().getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        for (SingleTokenDescription tokenDesc : testScenarioParams.getTokens()) {
            Token token = lex.lexToken();
            assertEquals(tokenDesc.getTokenType(), token.getTokenType());
            assertEquals(tokenDesc.getValue(), token.getValue());
            assertEquals(tokenDesc.getLineNumber(), token.getPosition().getLineNumber());
            assertEquals(tokenDesc.getColumnNumber(), token.getPosition().getColumnNumber());
        }
    }

    static Stream<Arguments> generateVariablesAssignmentTokensData() {
        return Stream.of(
                Arguments.of(new MultipleTokensTestParams("Int a = 4 - 9;",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.INT_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "a", 1, 5),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 7),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 4, 1, 9),
                                new SingleTokenDescription(TokenTypeEnum.SUBTRACTION_OPERATOR, 1, 11),
                                new SingleTokenDescription(TokenTypeEnum.INT_VALUE, 9, 1, 13),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 14)))),
                Arguments.of(new MultipleTokensTestParams("Double d = (4.04 / 29.012);",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.DOUBLE_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "d", 1, 8),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 10),
                                new SingleTokenDescription(TokenTypeEnum.LEFT_BRACKET, 1, 12),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 4.04, 1, 13),
                                new SingleTokenDescription(TokenTypeEnum.DIVISION_OPERATOR, 1, 18),
                                new SingleTokenDescription(TokenTypeEnum.DOUBLE_VALUE, 29.012, 1, 20),
                                new SingleTokenDescription(TokenTypeEnum.RIGHT_BRACKET, 1, 26),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 27)))),
                Arguments.of(new MultipleTokensTestParams("String my_text = \"4 - 9 abcd\";",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.STRING_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "my_text", 1, 8),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 16),
                                new SingleTokenDescription(TokenTypeEnum.STRING_VALUE, "4 - 9 abcd", 1, 18),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 1, 30)))),
                Arguments.of(new MultipleTokensTestParams("Bool boolean_Val_ = \n\n\n True\n;",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.BOOL_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "boolean_Val_", 1, 6),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 19),
                                new SingleTokenDescription(TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD, 4, 2),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 5, 1)))),
                Arguments.of(new MultipleTokensTestParams("Bool boolean_Val_ = \n\n\n False\n;",
                        Arrays.asList(new SingleTokenDescription(TokenTypeEnum.BOOL_KEYWORD, 1, 1),
                                new SingleTokenDescription(TokenTypeEnum.IDENTIFIER, "boolean_Val_", 1, 6),
                                new SingleTokenDescription(TokenTypeEnum.ASSIGNMENT_OPERATOR, 1, 19),
                                new SingleTokenDescription(TokenTypeEnum.BOOL_FALSE_VALUE_KEYWORD, 4, 2),
                                new SingleTokenDescription(TokenTypeEnum.SEMICOLON, 5, 1))))
        );
    }

    @Test
    void lexTwoIntsAdditionAndComparisonWithThirdInt() {
        InputStream inputStream = new ByteArrayInputStream("4 + 3 >= 81".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);


        Token valueToken = lex.lexToken();
        assertEquals(IntegerToken.class, valueToken.getClass());
        assertEquals(valueToken.getTokenType(), TokenTypeEnum.INT_VALUE);
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
        assertEquals(secondValueToken.getTokenType(), TokenTypeEnum.INT_VALUE);
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
        assertEquals(thirdValueToken.getTokenType(), TokenTypeEnum.INT_VALUE);
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
        assertEquals(token.getTokenType(), TokenTypeEnum.DOUBLE_VALUE);
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
        assertEquals(token.getTokenType(), TokenTypeEnum.DOUBLE_VALUE);
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
        assertEquals(token.getTokenType(), TokenTypeEnum.INT_VALUE);
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
        assertEquals(token.getTokenType(), TokenTypeEnum.BOOL_TRUE_VALUE_KEYWORD);
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
