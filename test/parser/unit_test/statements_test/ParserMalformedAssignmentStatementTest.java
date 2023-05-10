package parser.unit_test.statements_test;

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
import parser.exceptions.MissingAssignmentOperatorException;
import parser.exceptions.MissingExpressionException;
import parser.exceptions.MissingSemicolonException;
import parser.utils.MockedExitErrorHandler;
import parser.utils.MockedLexer;
import parser.utils.ParserMalformedSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedAssignmentStatementTest {

    private ArrayList<Token> startTokens;

    static Stream<Arguments> getMalformedAssignmentStatement_withCriticalExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingExpressionException(new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingAssignmentOperatorException(new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
                                )
                        )
                )
        );
    }

    static Stream<Arguments> getMalformedAssignmentStatement_withHandleableExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(5, 7), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("A", new Position(7, 10), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(8, 4), TokenTypeEnum.ASSIGNMENT_OPERATOR),
                                        new IntegerToken(1, new Position(8, 14)),
                                        new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingSemicolonException(new Token(new Position(9, 4), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
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
                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getMalformedAssignmentStatement_withCriticalExceptions")
    void parseMalformedAssignmentStatement_withCriticalExceptions(ParserMalformedSingleTestParams additionalParams) {
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

    @ParameterizedTest
    @MethodSource("getMalformedAssignmentStatement_withHandleableExceptions")
    void parseMalformedAssignmentStatement_withHandleableExceptions(ParserMalformedSingleTestParams additionalParams) {
        ArrayList<Token> testTokens = new ArrayList<>(startTokens);
        testTokens.addAll(additionalParams.tokens());
        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
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
