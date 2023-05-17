package parser.unit_test;

import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.exceptions.MissingLeftCurlyBracketException;
import parser.exceptions.MissingRightCurlyBracketException;
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

public class ParserMalformedCodeBlockTest {
    static Stream<Arguments> getMalformedTestCodeBlockData_withHandleableExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 7), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.RETURN_KEYWORD),
                                        new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingSemicolonException(new Token(new Position(3, 1), TokenTypeEnum.RIGHT_CURLY_BRACKET).toString())
                                )
                        )
                )
        );
    }

    static Stream<Arguments> getMalformedTestCodeBlockData_withCriticalExceptions() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 7), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingLeftCurlyBracketException(new Token(new Position(1, 10), TokenTypeEnum.ETX).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("func", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 7), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.LEFT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new MissingRightCurlyBracketException(new Token(new Position(2, 1), TokenTypeEnum.ETX).toString())
                                )
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getMalformedTestCodeBlockData_withHandleableExceptions")
    void parseMalformedFunctionDefProgram_withHandleableExceptions(ParserMalformedSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(tokens), errorHandler);
        parser.parse();

        Iterator<Exception> expected = testParams.expectedErrorLog().iterator();
        Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
        assertEquals(testParams.expectedErrorLog().size(), errorHandler.getErrorLog().size());
        while (expected.hasNext() && actual.hasNext()) {
            assertEquals(expected.next().getMessage(), actual.next().getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("getMalformedTestCodeBlockData_withCriticalExceptions")
    void parseMalformedFunctionDefProgram_withCriticalExceptions(ParserMalformedSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(tokens), errorHandler);
        boolean wasExceptionCaught = false;

        try {
            parser.parse();
        } catch (RuntimeException e) {
            wasExceptionCaught = true;
            Iterator<Exception> expected = testParams.expectedErrorLog().iterator();
            Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
            assertEquals(testParams.expectedErrorLog().size(), errorHandler.getErrorLog().size());
            while (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next().getMessage(), actual.next().getMessage());
            }
        }

        assert wasExceptionCaught;
    }
}
