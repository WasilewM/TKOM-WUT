package parser.unit_test.statements_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.exceptions.MissingCodeBlockException;
import parser.exceptions.MissingExpressionException;
import parser.exceptions.MissingLeftBracketException;
import parser.exceptions.MissingRightBracketException;
import parser.utils.MockedExitParserErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserMalformedSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedIfStatementTest {

    private ArrayList<Token> startTokens;

    public static Stream<Arguments> getMalformedIfStatement_withHandleableExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new StringToken("a", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(11, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                Arrays.asList(
                                        new MissingLeftBracketException(new StringToken("a", new Position(2, 5), TokenTypeEnum.IDENTIFIER).toString()),
                                        new MissingRightBracketException(new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("b", new Position(3, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(11, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingRightBracketException(new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("b", new Position(3, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(11, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(12, 3), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new StringToken("B", new Position(13, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(15, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                Arrays.asList(
                                        new MissingLeftBracketException(new StringToken("B", new Position(13, 5), TokenTypeEnum.IDENTIFIER).toString()),
                                        new MissingRightBracketException(new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(4, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("c", new Position(5, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(6, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(11, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(12, 3), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(13, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("B", new Position(13, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(15, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingRightBracketException(new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(4, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("c", new Position(5, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(6, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(7, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(8, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(10, 3), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(10, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("B", new Position(13, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(15, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(20, 3), TokenTypeEnum.ELSE_KEYWORD),
                                        new Token(new Position(26, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(27, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingRightBracketException(new Token(new Position(14, 1), TokenTypeEnum.LEFT_CURLY_BRACKET).toString())
                                )
                        )
                )
        );
    }

    static Stream<Arguments> getMalformedIfStatement_withCriticalExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 7), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("b", new Position(3, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(4, 12), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(4, 22), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(5, 3), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(6, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(7, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(7, 2), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 4), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("a", new Position(2, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingCodeBlockException(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(4, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("c", new Position(5, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(6, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(10, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(11, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(12, 3), TokenTypeEnum.ELSE_IF_KEYWORD),
                                        new Token(new Position(13, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("B", new Position(13, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(14, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingCodeBlockException(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("b", new Position(3, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 2), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(11, 1), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(12, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(13, 1), TokenTypeEnum.ELSE_KEYWORD),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingCodeBlockException(new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
                                )
                        )
                )
        );
    }

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

    @ParameterizedTest
    @MethodSource("getMalformedIfStatement_withHandleableExceptions")
    void parseMalformedIfStatement_withHandleableExceptions(ParserMalformedSingleTestParams additionalParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalParams.tokens());
        MockedExitParserErrorHandler errorHandler = new MockedExitParserErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        parser.parse();

        Iterator<Exception> expected = additionalParams.expectedErrorLog().iterator();
        Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
        assertEquals(additionalParams.expectedErrorLog().size(), errorHandler.getErrorLog().size());
        while (expected.hasNext() && actual.hasNext()) {
            assertEquals(expected.next().getMessage(), actual.next().getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("getMalformedIfStatement_withCriticalExceptions")
    void parseMalformedIfStatement_withCriticalExceptions(ParserMalformedSingleTestParams additionalParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalParams.tokens());
        MockedExitParserErrorHandler errorHandler = new MockedExitParserErrorHandler();
        Parser parser = new Parser(new MockedLexer(testTokens), errorHandler);
        boolean wasExceptionCaught = false;

        try {
            parser.parse();
        } catch (RuntimeException e) {
            wasExceptionCaught = true;
            Iterator<Exception> expected = additionalParams.expectedErrorLog().iterator();
            Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
            assertEquals(additionalParams.expectedErrorLog().size(), errorHandler.getErrorLog().size());
            while (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next().getMessage(), actual.next().getMessage());
            }
        }

        assert wasExceptionCaught;
    }
}
