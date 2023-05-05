package parser.unit_test.expressions_test;

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
import parser.exceptions.UnclearExpressionException;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;
import utils.ParserMalformedSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedComparisonExpressionTest {

    private ArrayList<Token> startTokens;

    public static Stream<Arguments> getMalformedComparisonExpTestData() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.LESS_THAN_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.LESS_THAN_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_OR_EQUAL_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.LESS_THAN_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.LESS_THAN_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.GREATER_THAN_OPERATOR),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.GREATER_THAN_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.GREATER_THAN_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.GREATER_OR_EQUAL_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.EQUAL_OPERATOR),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.EQUAL_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.EQUAL_OPERATOR).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.NOT_EQUAL_OPERATOR),
                                        new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(50, 1), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(2, 1), TokenTypeEnum.LESS_THAN_OPERATOR),
                                        new StringToken("a", new Position(3, 1), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(4, 1), TokenTypeEnum.NOT_EQUAL_OPERATOR)
                                ),
                                List.of(
                                        new UnclearExpressionException(new Token(new Position(4, 1), TokenTypeEnum.NOT_EQUAL_OPERATOR).toString())
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
                        new Token(new Position(1, 12), TokenTypeEnum.IF_KEYWORD),
                        new Token(new Position(1, 15), TokenTypeEnum.LEFT_BRACKET),
                        new StringToken("ident", new Position(1, 16), TokenTypeEnum.IDENTIFIER)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getMalformedComparisonExpTestData")
    void parseMalformedComparisonExpression_missingRightExp(ParserMalformedSingleTestParams additionalParams) {
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
