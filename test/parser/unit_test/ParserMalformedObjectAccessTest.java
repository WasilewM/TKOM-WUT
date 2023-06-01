package parser.unit_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.IntegerToken;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.exceptions.MissingExpressionException;
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

public class ParserMalformedObjectAccessTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getMalformedObjectAccess_withCriticalExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(10, 5), TokenTypeEnum.RETURN_KEYWORD),
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new Token(new Position(10, 100), TokenTypeEnum.SEMICOLON)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(10, 100), TokenTypeEnum.SEMICOLON).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.DOT),
                                        new StringToken("func2", new Position(12, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(12, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new IntegerToken(4, new Position(12, 19)),
                                        new Token(new Position(12, 20), TokenTypeEnum.COMMA),
                                        new Token(new Position(13, 10), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(13, 10), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                )
        );
    }

    static Stream<Arguments> getMalformedObjectAccess_withHandleableExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(10, 5), TokenTypeEnum.RETURN_KEYWORD),
                                        new StringToken("func1", new Position(10, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(10, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(13, 7), TokenTypeEnum.SEMICOLON),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(100, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingRightBracketException(new Token(new Position(13, 7), TokenTypeEnum.SEMICOLON).toString())
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
                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getMalformedObjectAccess_withCriticalExceptions")
    void getMalformedObjectAccess_withCriticalExceptions(ParserMalformedSingleTestParams additionalParams) {
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

    @ParameterizedTest
    @MethodSource("getMalformedObjectAccess_withHandleableExceptions")
    void parseMalformedFunctionDefProgram_withHandleableExceptions(ParserMalformedSingleTestParams additionalParams) {
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
}
