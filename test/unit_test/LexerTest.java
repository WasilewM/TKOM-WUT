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

        assertEquals(TokenTypeEnum.INTEGER, token.getTokenType());
        assertEquals(IntegerToken.class, token.getClass());
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

        assertEquals(TokenTypeEnum.INTEGER, token.getTokenType());
        assertEquals(IntegerToken.class, token.getClass());
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

        assertEquals(TokenTypeEnum.INTEGER, token.getTokenType());
        assertEquals(IntegerToken.class, token.getClass());
        assertEquals(10, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(9, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleGreaterThanOne() {
        InputStream inputStream = new ByteArrayInputStream("92.456".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(92.456, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }
    @Test
    void lexDoubleCloseToOne() {
        InputStream inputStream = new ByteArrayInputStream("1.0012".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(1.0012, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleOfValueBetweenZeroAndOne() {
        InputStream inputStream = new ByteArrayInputStream("0.00054".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(0.00054, token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleAfterWhitespacesAndNewline() {
        InputStream inputStream = new ByteArrayInputStream("   \n 103.72\n".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(103.72, token.getValue());
        assertEquals(2, token.getPosition().getLineNumber());
        assertEquals(2, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleAfterMultipleWhitespacesAndNewlines() {
        InputStream inputStream = new ByteArrayInputStream("   \n \n\n    103.72\n".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE, token.getTokenType());
        assertEquals(DoubleToken.class, token.getClass());
        assertEquals(103.72, token.getValue());
        assertEquals(4, token.getPosition().getLineNumber());
        assertEquals(5, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSingleLetterString() {
        InputStream inputStream = new ByteArrayInputStream("\"a\"".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.STRING, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertEquals("a", token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSingleLetterStringWithUnclosedQuotes() {
        InputStream inputStream = new ByteArrayInputStream("\"a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        assertNull(lex.lexToken());
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
    void lexAdditionOperator() {
        InputStream inputStream = new ByteArrayInputStream("+".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.ADDITION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexSubtractionOperator() {
        InputStream inputStream = new ByteArrayInputStream("-".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.SUBTRACTION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexMultiplicationOperator() {
        InputStream inputStream = new ByteArrayInputStream("*".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.MULTIPLICATION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDivisionOperator() {
        InputStream inputStream = new ByteArrayInputStream("/".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DIVISION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDivisionOperatorWhenDivisionFollowedByOtherChar() {
        InputStream inputStream = new ByteArrayInputStream("/a".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DIVISION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDiscreteDivisionOperator() {
        InputStream inputStream = new ByteArrayInputStream("//".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DISCRETE_DIVISION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexAssignmentOperator() {
        InputStream inputStream = new ByteArrayInputStream("=".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.ASSIGNMENT_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexAssignmentOperatorAfterWhitespacesAndNewlines() {
        InputStream inputStream = new ByteArrayInputStream("\n\n \n   =".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.ASSIGNMENT_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(4, token.getPosition().getLineNumber());
        assertEquals(4, token.getPosition().getColumnNumber());
    }

    @Test
    void lexEqualOperator() {
        InputStream inputStream = new ByteArrayInputStream("==".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.EQUAL_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexNotEqualOperator() {
        InputStream inputStream = new ByteArrayInputStream("!=".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.NOT_EQUAL_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexTokenWhenNotEqualOperatorIsMalformedButNegationOperatorIsValid() {
        InputStream inputStream = new ByteArrayInputStream("!-".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.NEGATION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLessThanOperator() {
        InputStream inputStream = new ByteArrayInputStream("<".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.LESS_THAN_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexLessOrEqualOperator() {
        InputStream inputStream = new ByteArrayInputStream("<=".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.LESS_OR_EQUAL_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexGreaterThanOperator() {
        InputStream inputStream = new ByteArrayInputStream(">".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.GREATER_THAN_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexGreaterOrEqualThanOperator() {
        InputStream inputStream = new ByteArrayInputStream(">=".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexAndOperator() {
        InputStream inputStream = new ByteArrayInputStream("&&".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.AND_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexTokenWhenAndOperatorIsMalformed() {
        InputStream inputStream = new ByteArrayInputStream("&-".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        assertNull(lex.lexToken());
    }

    @Test
    void lexOrOperator() {
        InputStream inputStream = new ByteArrayInputStream("||".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.OR_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexTokenWhenOrOperatorIsMalformed() {
        InputStream inputStream = new ByteArrayInputStream("|-".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        assertNull(lex.lexToken());
    }

    @Test
    void lexNegationOperator() {
        InputStream inputStream = new ByteArrayInputStream("!".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.NEGATION_OPERATOR, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexIntKeyword() {
        InputStream inputStream = new ByteArrayInputStream("Int".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.INT_KEYWORD, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexDoubleKeyword() {
        InputStream inputStream = new ByteArrayInputStream("Double".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.DOUBLE_KEYWORD, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexStringKeyword() {
        InputStream inputStream = new ByteArrayInputStream("String".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);
        Token token = lex.lexToken();

        assertEquals(TokenTypeEnum.STRING_KEYWORD, token.getTokenType());
        assertEquals(StringToken.class, token.getClass());
        assertNull(token.getValue());
        assertEquals(1, token.getPosition().getLineNumber());
        assertEquals(1, token.getPosition().getColumnNumber());
    }

    @Test
    void lexUnknownCharacter() {
        InputStream inputStream = new ByteArrayInputStream("|".getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        Lexer lex = new Lexer(bufferedInputStream);

        assertNull(lex.lexToken());
    }
}
