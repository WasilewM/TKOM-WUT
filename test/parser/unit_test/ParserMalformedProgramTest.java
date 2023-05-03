import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.exceptions.*;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;
import utils.ParserMalformedSingleTestParams;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedProgramTest {

    @ParameterizedTest
    @MethodSource("generateMalformedTestProgramData")
    void parseMalformedFunctionDefProgram(ParserMalformedSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(tokens), errorHandler);
        boolean wasExceptionCaught = false;

        try {
            parser.parse();
        }
        catch (RuntimeException e) {
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

    static Stream<Arguments> generateMalformedTestProgramData() {
        return Stream.of(
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                List.of(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD)
                                ),
                                List.of(
                                        new MissingIdentifierException(new Token(new Position(1, 1), TokenTypeEnum.ETX).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER)
                                ),
                                Arrays.asList(
                                        new MissingLeftBracketException(new Token(new Position(1, 5), TokenTypeEnum.ETX).toString()),
                                        new MissingRightBracketException(new Token(new Position(1, 5), TokenTypeEnum.ETX).toString()),
                                        new MissingLeftCurlyBracketException(new Token(new Position(1, 5), TokenTypeEnum.ETX).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET)
                                        ),
                                Arrays.asList(
                                        new MissingRightBracketException(new Token(new Position(1, 9), TokenTypeEnum.ETX).toString()),
                                        new MissingLeftCurlyBracketException(new Token(new Position(1, 9), TokenTypeEnum.ETX).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
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
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET)
                                        ),
                                List.of(
                                        new MissingRightCurlyBracketException(new Token(new Position(1, 11), TokenTypeEnum.ETX).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_CURLY_BRACKET),
                                        new Token(new Position(2, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(2, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(2, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(2, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(2, 12), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                List.of(
                                        new DuplicatedFunctionNameException("Function main at position: <line: 2, column 12>")
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.INT_KEYWORD),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingIdentifierException(new Token(new Position(1, 14), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("cube", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("a", new Position(1, 14), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 15), TokenTypeEnum.COMMA),
                                        new Token(new Position(1, 16), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingDataTypeDeclarationException(new Token(new Position(1, 16), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("cube", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("a", new Position(1, 14), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 15), TokenTypeEnum.COMMA),
                                        new Token(new Position(1, 16), TokenTypeEnum.INT_KEYWORD),
                                        new Token(new Position(1, 19), TokenTypeEnum.RIGHT_BRACKET)
                                ),
                                List.of(
                                        new MissingIdentifierException(new Token(new Position(1, 19), TokenTypeEnum.RIGHT_BRACKET).toString())
                                )
                        )
                ),
                Arguments.of(
                        new ParserMalformedSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("cube", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("a", new Position(1, 14), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 15), TokenTypeEnum.COMMA),
                                        new Token(new Position(1, 16), TokenTypeEnum.BOOL_KEYWORD),
                                        new StringToken("a", new Position(1, 21), TokenTypeEnum.IDENTIFIER)
                                ),
                                List.of(
                                        new DuplicatedParameterNameException("Parameter a at position: <line: 1, column 21>")
                                )
                        )
                )
        );
    }
}
