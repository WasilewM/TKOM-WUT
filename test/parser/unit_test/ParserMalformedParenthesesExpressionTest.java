package parser.unit_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.exceptions.MissingExpressionException;
import parser.exceptions.UnclosedParenthesesException;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserMalformedSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedParenthesesExpressionTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getMalformedParenthesesExpression_withCriticalExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(7, 10), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(7, 10), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(4, 1), TokenTypeEnum.LEFT_BRACKET),
                                        new StringToken("a", new Position(4, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(5, 1), TokenTypeEnum.SEMICOLON)
                                ),
                                List.of(
                                        new UnclosedParenthesesException(new Token(new Position(5, 1), TokenTypeEnum.SEMICOLON).toString())
                                )
                        )
                )
        );
    }

    @BeforeEach
    public void initTestParams() {
        startTokens = new ArrayList<>(
                Arrays.asList(
                        new Token(new Position(1, 1), TokenTypeEnum.BOOL_KEYWORD),
                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getMalformedParenthesesExpression_withCriticalExceptions")
    void parseMalformedParenthesesExpression_withCriticalExceptions(ParserMalformedSingleTestParams additionalParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalParams.tokens());
        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
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
