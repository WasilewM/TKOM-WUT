import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.exceptions.MissingLeftBracketException;
import parser.exceptions.MissingRightBracketException;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedIfStatementTest {

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
                        new Token(new Position(2, 1), TokenTypeEnum.IF_KEYWORD)
                )
        );
    }

    @Test
    void parseIfStatementWithMissingLeftBracket() {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.add(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET));
        List<Exception> expectedErrorLog = Arrays.asList(
                new MissingLeftBracketException(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString()),
                new MissingRightBracketException(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
        );

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        parser.parse();

        Iterator<Exception> expected = expectedErrorLog.iterator();
        Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
        assertEquals(expectedErrorLog.size(), errorHandler.getErrorLog().size());
        while (expected.hasNext() && actual.hasNext()) {
            assertEquals(expected.next().getMessage(), actual.next().getMessage());
        }
    }

    @Test
    void parseIfStatementWithMissingRightBracket() {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.add(new Token(new Position(10, 1), TokenTypeEnum.LEFT_BRACKET));
        testTokens.add(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET));
        List<Exception> expectedErrorLog = List.of(
                new MissingRightBracketException(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
        );

        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        parser.parse();

        Iterator<Exception> expected = expectedErrorLog.iterator();
        Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
        assertEquals(expectedErrorLog.size(), errorHandler.getErrorLog().size());
        while (expected.hasNext() && actual.hasNext()) {
            assertEquals(expected.next().getMessage(), actual.next().getMessage());
        }
    }

}
