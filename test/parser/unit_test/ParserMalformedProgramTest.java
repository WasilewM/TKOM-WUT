import lexer.Position;
import lexer.TokenTypeEnum;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.exceptions.MissingIdentifierException;
import parser.exceptions.MissingLeftBracketException;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;
import utils.ParserMalformedSingleTestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserMalformedProgramTest {

    @ParameterizedTest
    @MethodSource("generateMalformedTestProgramData")
    void parseMalformedFunctionDefProgram(ParserMalformedSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        MockedExitErrorHandler errorHandler = new MockedExitErrorHandler();
        Parser parser = new Parser(new MockedLexer(tokens), errorHandler);

        try {
            parser.parse();
        }
        catch (RuntimeException e) {
            Iterator<Exception> expected = testParams.expectedErrorLog().iterator();
            Iterator<Exception> actual = errorHandler.getErrorLog().iterator();
            assertEquals(testParams.expectedErrorLog().size(), errorHandler.getErrorLog().size());
            while (expected.hasNext() && actual.hasNext()) {
                assertEquals(expected.next().getMessage(), actual.next().getMessage());
            }
        }
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
                                List.of(
                                        new MissingLeftBracketException(new Token(new Position(1, 5), TokenTypeEnum.ETX).toString())
                                )
                        )
                )
        );
    }
}
