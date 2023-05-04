import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.exceptions.MissingExpressionException;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedAlternativeExpressionTest {

    private ArrayList<Token> startTokens;

    @BeforeEach
    public void initTestParams() {
        startTokens = new ArrayList<>(
                Arrays.asList(
                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                        new Token(new Position(1, 12), TokenTypeEnum.IF_KEYWORD),
                        new Token(new Position(1, 15), TokenTypeEnum.LEFT_BRACKET),
                        new StringToken("ident", new Position(1, 16), TokenTypeEnum.IDENTIFIER)
                )
        );
    }

    @Test
    void parseMalformedAlternativeExpression_missingRightExp() {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);

        testTokens.add(new Token(new Position(2, 1), TokenTypeEnum.OR_OPERATOR));
        testTokens.add(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET));
        List<Exception> expectedErrorLog = List.of(
                new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
        );

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        boolean wasExceptionCaught = false;

        try {
            parser.parse();
        } catch (RuntimeException e) {
            wasExceptionCaught = true;
            Iterator<Exception> expected = expectedErrorLog.iterator();
            Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
            assertEquals(expectedErrorLog.size(), errorHandler.getErrorLog().size());
            while (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next().getMessage(), actual.next().getMessage());
            }
        }

        assert wasExceptionCaught;
    }
}
